package caterpillow.trainer.scenarios;

import caterpillow.AimTrainer;
import caterpillow.trainer.bots.BotInfo;
import caterpillow.trainer.map.MapInfo;
import caterpillow.trainer.player.PlayerInfo;

import java.io.Serializable;

public class Scenario {

    // this will contain all the information needed to load and play a scenario

    private String version = "v1.0 b1";

    private String name;
    private String creator;

    private MapInfo mapInfo;
    private PlayerInfo playerInfo;
    private BotInfo botInfo;

    public Scenario(String name, String creator, MapInfo mapinfo, PlayerInfo playerInfo, BotInfo botInfo) {
        this.name = name;
        this.creator = creator;
        this.mapInfo = mapinfo;
        this.playerInfo = playerInfo;
        this.botInfo = botInfo;
    }

    public Scenario() {
    }


    public MapInfo getMapInfo() {
        return mapInfo;
    }

    public void setMapInfo(MapInfo mapInfo) {
        this.mapInfo = mapInfo;
    }

    public PlayerInfo getPlayerInfo() {
        return playerInfo;
    }

    public void setPlayerInfo(PlayerInfo playerInfo) {
        this.playerInfo = playerInfo;
    }

    public BotInfo getBotInfo() {
        return botInfo;
    }

    public void setBotInfo(BotInfo botInfo) {
        this.botInfo = botInfo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }


}
