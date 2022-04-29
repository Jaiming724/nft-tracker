package dev.scratch.nfttracker.model.stats;

import com.google.gson.annotations.SerializedName;

public class Stats {
    @SerializedName("stats")
    private StatsData statsHelper;

    public StatsData getStatsHelper() {
        return statsHelper;
    }

    public void setStatsHelper(StatsData statsHelper) {
        this.statsHelper = statsHelper;

    }
}

