package dev.scratch.nfttracker.service;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import dev.scratch.nfttracker.model.mongo.Image;
import dev.scratch.nfttracker.model.mongo.NFTMongo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.*;
import java.net.URL;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

import static java.util.Collections.singletonList;

@Service
public class ImageService {
    private static Logger logger = LoggerFactory.getLogger(ImageService.class);
    private final Cloudinary cloudinary;
    private final NFTService nftService;

    @Autowired
    public ImageService(Cloudinary cloudinary, NFTService nftService) {
        this.cloudinary = cloudinary;
        this.nftService = nftService;
    }

    public CompletableFuture<String> getImage(String fileName, String filePath, NFTMongo nftMongo, int tokenID) {
        return CompletableFuture.supplyAsync(() -> fetchImage(fileName, filePath, nftMongo, tokenID))
                .exceptionally(throwable -> {
                    logger.error("Exception:", throwable);
                    return null;
                })
                .completeOnTimeout(null, 45, TimeUnit.SECONDS);
    }

    private String fetchImage(String fileName, String filePath, NFTMongo nftMongo, int tokenID) {
        String linkToNFT = nftMongo.getMediaLink();

        if (linkToNFT == null) {
            logger.warn("Could not get image, nft gateway was null for {} {}", nftMongo.getName(), tokenID);
            return null;
        }
        if (nftMongo.getContractAddress() == null) {
            logger.warn("Could not get image, nft contract address was null for {} {}", nftMongo.getName(), tokenID);
            return null;
        }
        String temp = nftService.getImageUrl(nftMongo.getName(), String.valueOf(tokenID));
        if (temp != null) {
            logger.info(String.format("%s %d image url found at %s", nftMongo.getName(), tokenID, temp));
            return temp;
        }
        logger.info("Downloading Image {} {} at {}", nftMongo.getName(), tokenID, linkToNFT);
        try {
            URL url = new URL(linkToNFT);
            InputStream in = new BufferedInputStream(url.openStream());

            OutputStream out = new BufferedOutputStream(new FileOutputStream(filePath));
            for (int i; (i = in.read()) != -1; ) {
                out.write(i);
            }
            in.close();
            out.close();
            Map uploadResult = cloudinary.uploader().upload(new File(filePath), ObjectUtils.asMap("public_id", fileName));
            String imageLink = (String) uploadResult.get("secure_url");
            nftMongo.setImage(singletonList(new Image(String.valueOf(tokenID), imageLink)));

            insertImageUrl(nftMongo, tokenID, imageLink);
            File file = new File(filePath);
            file.delete();
            return imageLink;
        } catch (IOException e) {
            logger.error("IOException", e);
        }
        return null;
    }

    private void insertImageUrl(NFTMongo nftMongo, int tokenID, String imageLink) {
        logger.info(String.format("inserting image url for %s %d at %s", nftMongo.getName(), tokenID, imageLink));

        if (!nftService.containNFt(nftMongo.getName())) {
            logger.info(String.format("No NFT with the name %s, inserting document", nftMongo.getName()));
            nftService.insert(nftMongo);

        } else {
            logger.info(String.format("Found NFT with the name %s, inserting sub doc", nftMongo.getName()));
            nftService.addImage(String.valueOf(tokenID), imageLink, nftMongo.getName());
        }

    }
}
