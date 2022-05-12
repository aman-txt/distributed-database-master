package reverseEngineering;

import DiskHandler.DistributedManager;
import utils.UtilsConstant;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;

public class ReverseEngineering {
    private final String mDatabaseFilePath = UtilsConstant.DATABASE_ROOT_FOLDER+"/";
    private HashMap<String, HashMap<String, String[]>> dependencyGraph = new HashMap<>();
    HashMap<String, Integer> tableRank = new HashMap<>();
    HashMap<Integer, Integer> relationships = new HashMap<>();
    HashMap<String, List<String>> mTableMetadata;
    int count = 0;


    public String[] getRankOrder(String databaseName) throws IOException {
        try {
            generateTableRank(databaseName);
            return createRelationships();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return new String[]{};
        }
    }

    private void generateTableRank(String databaseName) throws IOException {

            mTableMetadata = getTableMetadata(databaseName, "metadata_");
            for (String tableName: mTableMetadata.keySet()) {
                dependencyGraph.put(tableName, null);
                tableRank.put(tableName.toLowerCase(), count++);
            }

    }

    public HashMap<String, List<String>> getTableMetadata(String databaseName, String filePrefix) throws IOException {
        HashMap<String, List<String>> databaseMetadata = new HashMap<>();
        try {
            File databaseFolder = new File(mDatabaseFilePath + databaseName+"/global_metadata.txt");
            String readLine = "";
            String tableName = "";
            if (databaseFolder.isFile()) {
                Scanner readFile = new Scanner(databaseFolder);
                while (readFile.hasNext()) {
                    readLine = readFile.nextLine();
                    if (readLine.startsWith(filePrefix)) {
                        tableName = readLine.split("_")[1].split("\\|")[0];
                        List<String> tableMetadata = DistributedManager.readFile(databaseName, mDatabaseFilePath + databaseName + "/" + readLine.split("\\|")[0], readLine.split("\\|")[0]);
                        databaseMetadata.put(tableName.substring(0, tableName.length() - 4), tableMetadata);
                    }
                }
            }
        }
        catch (FileNotFoundException e){
            e.printStackTrace();
        }
        return databaseMetadata;
    }

    public String[] createRelationships() {
        for (String tableName: mTableMetadata.keySet()) {
            readTableMetadata(mTableMetadata.get(tableName), tableName);
        }

        int[][] ints = new int[relationships.size()][2];
        int i = 0;
        for (Integer keys : relationships.keySet()) {
            ints[i][0] = keys;
            ints[i][1] = relationships.get(keys);
            i++;
        }
        TopographicalSort topographicalSort = new TopographicalSort();
        List<Integer> tablePriorityOrder = topographicalSort.canFinish(relationships.size() + 1, ints);
        String[] rankedTables = new String[tableRank.size()];
        int count = 0;
        for (int j = tablePriorityOrder.size() - 1; j >= 0; j--) {
            for (Map.Entry<String, Integer> tableRankEntry: tableRank.entrySet()) {
                if (Objects.equals(tablePriorityOrder.get(j), tableRankEntry.getValue())) {
                    rankedTables[count++] = tableRankEntry.getKey();
                }
            }
        }
        return rankedTables;
    }

    private void readTableMetadata(List<String> tableMetadata, String tableName) {
        String[] columnDesc;
        int columnDescLength;
        String cardinality;
        HashMap<String, String[]> dependencyHashMap = new HashMap<>();

            for(String metadata: tableMetadata) {
                columnDesc = metadata.split("[|]");
                columnDescLength = columnDesc.length;
                if (columnDescLength > 4) {
                    if (columnDesc[3].equals("PK"))
                        cardinality = "1:N";
                    else cardinality = "N:M";
                    dependencyHashMap.put(columnDesc[5],new String[]{columnDesc[5], columnDesc[6], cardinality});
                    relationships.put(tableRank.get(tableName), tableRank.get(columnDesc[5].toLowerCase()));
                }
            }
            dependencyGraph.put(tableName, dependencyHashMap);

    }

    public HashMap<String, HashMap<String, String[]>> getDependencyGraph() {
        return dependencyGraph;
    }
}