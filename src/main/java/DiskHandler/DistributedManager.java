package DiskHandler;

import utils.UtilsConstant;
import utils.UtilsFileHandler;

import java.io.*;
import java.util.List;

public class DistributedManager
{

    private static final String REMOTE_DATABASE_PATH = "/home/amansbhandari/dmwaproject/";
    /**
     * returns which instance does a file exists. -1 when it doesn't exists in either
     * @param database
     * @param filename name of the file you are looking
     * @return
     * @throws IOException
     */
    private static String whichInstance(String database , String filename) throws IOException
    {
        if(database.isEmpty())
            return "-1";

        String fullPathGM = UtilsConstant.DATABASE_ROOT_FOLDER+"/"+ database + "/"+ UtilsConstant.GM_FILE_NAME;

            List<String> content = UtilsFileHandler.readFile(fullPathGM);

            for(String line : content)
            {
                String[] elements = line.split("[|]");
                if(elements[0].equals(filename))
                {
                    return elements[1];
                }
            }

        return "-1";
    }

    /**
     * reads and return the content of the file line by line. Caller doesn't need to care about which instance the file is located.
     * @param database
     * @param fullpath path of the file you are looking for. For e.g. database/University/metadata_students.txt
     * @param filename just the file name with extension you are looking for. For e.g. metadata_students.txt
     * @return
     * @throws IOException
     */
    public static List<String> readFile(String database, String fullpath, String filename) throws IOException
    {
        String instanceOfFile = whichInstance(database, filename);
        try {
            List<String> myInstance = UtilsFileHandler.readFile("instances/local.txt");
            List<String> otherInstance = UtilsFileHandler.readFile("instances/remote.txt");

            if(instanceOfFile.equals(myInstance.get(0)) || filename.startsWith("global_") || fullpath.startsWith("logs") || fullpath.startsWith("analytics"))    //The file is in current instance
            {
                return UtilsFileHandler.readFile(fullpath);
            }

            fullpath =  REMOTE_DATABASE_PATH + fullpath;
            //The file is in remote instance
            return RemoteHandler.executeCommand("cat "+fullpath , otherInstance.get(1));

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * reads and return the content of the file line by line. Caller doesn't need to care about which instance the file is located.
     * @param database
     * @param fullpath path of the file you are looking for. For e.g. database/University/metadata_students.txt
     * @param filename just the file name with extension you are looking for. For e.g. metadata_students.txt
     * @return
     * @throws IOException
     */
    public static Boolean writeFile(String database, String fullpath, String filename, String content) throws IOException
    {
        String instanceOfFile = whichInstance(database, filename);


        try {
            List<String> myInstance = UtilsFileHandler.readFile("instances/local.txt");
            List<String> otherInstance = UtilsFileHandler.readFile("instances/remote.txt");

            if(instanceOfFile.equals(myInstance.get(0)) || filename.startsWith("global_") || fullpath.startsWith("logs") || fullpath.startsWith("dump") || fullpath.startsWith("analytics"))    //The file is in current instance
            {
                UtilsFileHandler.writeToFile(fullpath, content);

                if(!filename.startsWith("global_") && !fullpath.startsWith("logs") && !fullpath.startsWith("dump") && !fullpath.startsWith("analytics"))
                    return true;
            }

            fullpath =  REMOTE_DATABASE_PATH + fullpath;
            //The file is in remote instance.. write it
            RemoteHandler.executeCommand("echo -e"  + " \""+ content + "\""+ "> " + fullpath , otherInstance.get(1));

        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return false;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * creates a new folder at the given path
     * @param fullpath path of the file you are looking for. For e.g. database/University/metadata_students.txt
     * @return
     * @throws IOException
     */
    public static Boolean createFolder(String fullpath) throws IOException
    {
        new File(fullpath).mkdirs();

        List<String> otherInstance = UtilsFileHandler.readFile("instances/remote.txt");

        //The file is in remote instance.. write it
        try {
            RemoteHandler.executeCommand("mkdir dmwaproject/" + fullpath , otherInstance.get(1));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }

    /**
     * creates an empty global_metadata.txt
     * @param fullpath path of the file you are looking for. For e.g. database/University/metadata_students.txt
     * @return
     * @throws IOException
     */
    public static Boolean createEmptyFile(String fullpath) throws IOException
    {
        PrintWriter writer = new PrintWriter(fullpath, "UTF-8");
        writer.close();

        List<String> otherInstance = UtilsFileHandler.readFile("instances/remote.txt");

        //The file is in remote instance.. write it
        try {
            RemoteHandler.executeCommand("touch dmwaproject/" + fullpath , otherInstance.get(1));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }

}
