package dev.scratch.nfttracker.model.collection;

public class PrimaryAssetContract {
    private String address;
    private String asset_contract_type;
    private String name;
    private String nft_version;
    private Object opensea_version;
    private int owner;
    private String schema_name;
    private String symbol;
    private String total_supply;
    private String description;
    private String external_link;
    private String image_url;
    private boolean default_to_fiat;
    private int dev_buyer_fee_basis_points;
    private int dev_seller_fee_basis_points;
    private boolean only_proxied_transfers;
    private int opensea_buyer_fee_basis_points;
    private int opensea_seller_fee_basis_points;
    private int buyer_fee_basis_points;
    private int seller_fee_basis_points;
    private String payout_address;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAsset_contract_type() {
        return asset_contract_type;
    }

    public void setAsset_contract_type(String asset_contract_type) {
        this.asset_contract_type = asset_contract_type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNft_version() {
        return nft_version;
    }

    public void setNft_version(String nft_version) {
        this.nft_version = nft_version;
    }

    public Object getOpensea_version() {
        return opensea_version;
    }

    public void setOpensea_version(Object opensea_version) {
        this.opensea_version = opensea_version;
    }

    public int getOwner() {
        return owner;
    }

    public void setOwner(int owner) {
        this.owner = owner;
    }

    public String getSchema_name() {
        return schema_name;
    }

    public void setSchema_name(String schema_name) {
        this.schema_name = schema_name;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getTotal_supply() {
        return total_supply;
    }

    public void setTotal_supply(String total_supply) {
        this.total_supply = total_supply;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getExternal_link() {
        return external_link;
    }

    public void setExternal_link(String external_link) {
        this.external_link = external_link;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    public boolean isDefault_to_fiat() {
        return default_to_fiat;
    }

    public void setDefault_to_fiat(boolean default_to_fiat) {
        this.default_to_fiat = default_to_fiat;
    }

    public int getDev_buyer_fee_basis_points() {
        return dev_buyer_fee_basis_points;
    }

    public void setDev_buyer_fee_basis_points(int dev_buyer_fee_basis_points) {
        this.dev_buyer_fee_basis_points = dev_buyer_fee_basis_points;
    }

    public int getDev_seller_fee_basis_points() {
        return dev_seller_fee_basis_points;
    }

    public void setDev_seller_fee_basis_points(int dev_seller_fee_basis_points) {
        this.dev_seller_fee_basis_points = dev_seller_fee_basis_points;
    }

    public boolean isOnly_proxied_transfers() {
        return only_proxied_transfers;
    }

    public void setOnly_proxied_transfers(boolean only_proxied_transfers) {
        this.only_proxied_transfers = only_proxied_transfers;
    }

    public int getOpensea_buyer_fee_basis_points() {
        return opensea_buyer_fee_basis_points;
    }

    public void setOpensea_buyer_fee_basis_points(int opensea_buyer_fee_basis_points) {
        this.opensea_buyer_fee_basis_points = opensea_buyer_fee_basis_points;
    }

    public int getOpensea_seller_fee_basis_points() {
        return opensea_seller_fee_basis_points;
    }

    public void setOpensea_seller_fee_basis_points(int opensea_seller_fee_basis_points) {
        this.opensea_seller_fee_basis_points = opensea_seller_fee_basis_points;
    }

    public int getBuyer_fee_basis_points() {
        return buyer_fee_basis_points;
    }

    public void setBuyer_fee_basis_points(int buyer_fee_basis_points) {
        this.buyer_fee_basis_points = buyer_fee_basis_points;
    }

    public int getSeller_fee_basis_points() {
        return seller_fee_basis_points;
    }

    public void setSeller_fee_basis_points(int seller_fee_basis_points) {
        this.seller_fee_basis_points = seller_fee_basis_points;
    }

    public String getPayout_address() {
        return payout_address;
    }

    public void setPayout_address(String payout_address) {
        this.payout_address = payout_address;
    }
}