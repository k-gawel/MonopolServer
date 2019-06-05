package org.california.monopolserver.model.pattern.board;

import org.california.monopolserver.model.pattern.landable.LandablePattern;
import org.california.monopolserver.model.pattern.landable.card.*;
import org.california.monopolserver.model.pattern.landable.town.TownPattern;
import org.california.monopolserver.model.pattern.landable.town.TownRegionPattern;
import org.california.monopolserver.model.pattern.landable.utility.UtilityPattern;
import org.california.monopolserver.model.pattern.landable.utility.UtilityRegionPattern;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.HashSet;
import java.util.stream.Stream;

public class BasicBoardPatternBuilder extends BoardPatternBuilder {

    private BoardPattern result = new BoardPattern();

    private Collection<TownRegionPattern> townRegions = new HashSet<>();
    private Collection<UtilityRegionPattern> utilityRegions = new HashSet<>();
    private Collection<CardGroupPattern> cardGroups = new HashSet<>();


    public BoardPattern build() {
        String jsonString = getJsonString();
        JSONObject jsonObject = new JSONObject(jsonString);
        JSONArray jsonFields = jsonObject.getJSONArray("board");
        return buildBoard(jsonFields);
    }


    private String getJsonString() {
        StringBuilder jsonStringBuilder = new StringBuilder();

        try(Stream<String> stream = Files.lines(Paths.get("/home/kamil/Projects/Monopol/Monopol-Server/src/main/resources/templates/basic-board.json"))) {
            stream.forEach(s -> jsonStringBuilder.append(s).append("\n"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        return jsonStringBuilder.toString();
    }


    private BoardPattern buildBoard(JSONArray array) {
        int fieldNumber = 0;

        for(Object object : array) {
            JSONObject jsonField = (JSONObject) object;
            LandablePattern landable = createLandablePattern(jsonField);
            result.fields.put(fieldNumber++, landable);
        }

        return result;
    }


    private LandablePattern createLandablePattern(JSONObject json) {
        String type = json.getString("type");

        switch (type) {
            case "TOWN":
                return createTown(json);
            case "UTILITY":
                return createUtility(json);
            case "CARD-GROUP":
                return createCardGroup(json);
            default:
                throw new JSONException("Field type [" + type + "] is invalid");
        }

    }


    private TownRegionPattern getTownRegion(String name) {
         TownRegionPattern result = townRegions.stream()
                .filter(t -> t.getName().equals(name))
                .findFirst()
                .orElse(new TownRegionPattern(name));
         townRegions.add(result);
         return result;
    }


    private UtilityRegionPattern getUtilityRegion(String name) {
        UtilityRegionPattern result = utilityRegions.stream()
                .filter(u -> u.getName().equals(name))
                .findFirst()
                .orElse(new UtilityRegionPattern(name));
        utilityRegions.add(result);
        return result;
    }


    private Surprisable createCard(CardGroupPattern cardGroup, JSONObject json) {
        String type = json.getString("type");
        int property = json.getInt("property");
        String description = json.getString("description");

        switch (type) {
            case "GIFT":
                return new GiftCard(cardGroup, description, property);
            case "MOVE":
                return new MoveCard(cardGroup, description, property);
            case "CHARGE":
                return new ChargeCard(cardGroup, description, property);
            default:
                throw new JSONException("Wrong card type");
        }
    }


    private CardGroupPattern createCardGroup(JSONObject json) {
        String name = json.getString("name");
        String description = json.getString("description");
        JSONArray cards = json.getJSONArray("cards");

        CardGroupPattern result = new CardGroupPattern(name, description);

        for(Object jsonCardObject : cards) {
            JSONObject jsonCard = (JSONObject) jsonCardObject;
            result.addComponent(createCard(result, jsonCard));
        }

        return result;
    }


    private TownPattern createTown(JSONObject json) {
        String name = json.getString("name");
        String regionName = json.getString("region");
        int basicPrice = json.getInt("basicPrice");

        return new TownPattern(getTownRegion(regionName), name, basicPrice);
    }


    private UtilityPattern createUtility(JSONObject json) {
        String name = json.getString("name");
        String regionName = json.getString("region");
        int basicPrice = json.getInt("basicPrice");

        return new UtilityPattern(getUtilityRegion(regionName), name, basicPrice);
    }


}
