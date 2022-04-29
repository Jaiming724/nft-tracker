package dev.scratch.nfttracker.util;

public class NoContractAddressException extends RuntimeException{
    public NoContractAddressException() {
        super("No Contract Address Found");
    }
}

