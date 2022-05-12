package reverseEngineering;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

public class DrawERD {
    private String erd = "";
    HashMap<String, List<String>> mTableMetadata;
    public String draw(String[] rankOrder, HashMap<String, List<String>> tableMetadata, HashMap<String, HashMap<String, String[]>> dependencyGraph) {
        erd = "";
        mTableMetadata = tableMetadata;
        for (int i = 0; i < rankOrder.length; i++) {
            if (rankOrder[i] == null)
                continue;
            erd += "-".repeat(35) + "\n";
            erd += "| " + rankOrder[i].toUpperCase() + " ".repeat(31 - rankOrder[i].length()) + " |\n";
            erd += "-".repeat(35) + "\n";
            readMetadata(rankOrder[i]);
            erd += "\n";
            if (i < rankOrder.length - 1 && rankOrder[i + 1] != null) {
                String[] relationship = dependencyGraph.get(rankOrder[i + 1]).get(rankOrder[i]);
                if (relationship != null) {
                    erd += (" ".repeat(17) + "|\n").repeat(2);
                    erd += " ".repeat(16) + relationship[2] + "\n";
                    erd += (" ".repeat(17) + "|\n").repeat(2);
                } else {
                    erd += "\n";
                }
            }
        }
        return erd;
    }

    private void readMetadata(String tableName) {
        List<String> tableMetadata = mTableMetadata.get(tableName);
        String[] columnDesc;
        for (String readFile : tableMetadata) {
            columnDesc = readFile.split("[|]");
            erd += "|  " + columnDesc[0] + " " + columnDesc[1] + " ".repeat(30 - (columnDesc[0] + columnDesc[1]).length()) + "|\n";
        }
        erd += "-".repeat(35);
    }
}
