package surredstone;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.configuration.file.YamlConfiguration;

public class Village {
    final String INVALID_VILLAGE_STORAGE_ERROR = "Invalid Village Storage";

    final int id;
    String name;
    String abbreviation;
    String color;
    String flag;

    static private List<Village> villages;

    private Village(
            final int id,
            String name,
            String abbreviation,
            String color,
            String flag) {
        if (abbreviation.contains(" ") || abbreviation.contains("   "))
            throw new Error(INVALID_VILLAGE_STORAGE_ERROR);

        this.id = id;
        this.name = name;
        this.abbreviation = abbreviation;
        this.color = color;
        this.flag = flag;
    }

    static private YamlConfiguration getVillageStorage() {
        return YamlConfiguration.loadConfiguration(new File(Plugin.getInstance().getDataFolder() + "villages.yml"));
    }

    static public List<Village> getAllVillages() {
        if (villages == null) {
            YamlConfiguration villageStorage = getVillageStorage();

            villages = new ArrayList<Village>();

            for (int i = 0; i < villageStorage.getKeys(false).size(); i++) {
                String idStringfied = String.valueOf(i);

                villages.add(new Village(
                        i,
                        villageStorage.getString(idStringfied + ".name"),
                        villageStorage.getString(idStringfied + ".abbreviation"),
                        villageStorage.getString(idStringfied + ".color"),
                        villageStorage.getString(idStringfied + ".flag")));
            }
        }

        return villages;
    }

    static public Village getVillageByAbbreviation(String abbreviation) {
        List<Village> villages = getAllVillages();

        return villages.stream().filter(village -> village.abbreviation == abbreviation).findFirst().orElse(null);
    }

    static public Village getVillageById(int id) {
        List<Village> villages = getAllVillages();

        return villages.stream().filter(village -> village.id == id).findFirst().orElse(null);
    }

    public String getPermission() {
        return "surredstone.villages" + getAbbreviation().toLowerCase();
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getAbbreviation() {
        return abbreviation;
    }

    public String getColor() {
        return color;
    }

    public String getFlag() {
        return flag;
    }
}