package DiskHandler;

import java.io.ByteArrayOutputStream;
import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

public class RemoteHandler {

    private static final String username = "amansbhandari";
    private static final int port = 22;
    //private static final String privateKey = "/Users/amansinghbhandari/Documents/gcp_keys/amansbhandari";
    private static final String privateKey = "/home/amansbhandari/keys/amansbhandari";


    public static List<String> executeCommand(String command, String host) throws Exception {
        Session session = null;
        ChannelExec channel = null;
        List<String> content = new ArrayList<>();

        try {
            JSch jSch = new JSch();
            int port = 22;
            jSch.addIdentity(privateKey);
            session = jSch.getSession(username, host, port);
            session.setConfig("StrictHostKeyChecking", "no");
            session.connect();

            channel = (ChannelExec) session.openChannel("exec");
            channel.setCommand(command);
            ByteArrayOutputStream responseStream = new ByteArrayOutputStream();
            channel.setOutputStream(responseStream);
            channel.connect();

            while (channel.isConnected()) {
                Thread.sleep(100);
            }


            String responseString = new String(responseStream.toByteArray());
            if(responseString.isEmpty())
            {
                content = new ArrayList();
            }
            else
            {
                String[] lines = responseString.split("[\\n]");
                for(String line : lines)
                {
                    content.add(line);
                }
            }


        } finally {
            if (session != null) {
                session.disconnect();
            }
            if (channel != null) {
                channel.disconnect();
            }
        }

        return content;
    }

}
