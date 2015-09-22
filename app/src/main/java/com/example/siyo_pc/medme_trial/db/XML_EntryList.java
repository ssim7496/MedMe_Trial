package com.example.siyo_pc.medme_trial.db;

import android.content.Context;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by Siyo-PC on 2015/09/22.
 */
public class XML_EntryList {
    private String xmlFromServer;
    private ArrayList<XML_Entry> entryList = new ArrayList<>();
    private Context context;

    public XML_EntryList(Context context) {
        this.context = context;
    }

    public XML_EntryList(Context context, String xmlFromServer) {
        this.context = context;
        this.xmlFromServer = xmlFromServer;
    }

    public String getXmlFromServer() { return this.xmlFromServer; }
    public void setXmlFromServer(String xmlFromServer) { this.xmlFromServer = xmlFromServer; }

    public ArrayList<XML_Entry> createEntryList() {


        return  entryList;
    }

    public void showNow() {
        String xmlWithNoNode = removeRootNode();

        boolean end = false;

        /*while (!end) {
            int entryStartNode = xmlWithNoNode.indexOf("<entry>");
        }*/
        //getting the current entry that we are working with
        int entryStartNode = xmlWithNoNode.indexOf(">");
        int entryEndNode = xmlWithNoNode.indexOf("</entry>");
        String currentXML = xmlWithNoNode.substring(entryStartNode + 1, entryEndNode);

        //getting the hw in the entry
        if (currentXML.contains("<hw>")) {
            int entryStartNodeHw = currentXML.indexOf("<hw>");
            int entryEndNodeHw = currentXML.indexOf("</hw>");
            String hw = currentXML.substring(entryStartNodeHw + 4, entryEndNodeHw);
            //assign hw to xml_entry class
            currentXML = currentXML.substring(entryEndNodeHw + 5, currentXML.length());
            Toast.makeText(context.getApplicationContext(), "hw: " + hw, Toast.LENGTH_LONG).show();
        }

        //getting the fl in the entry
        if (currentXML.contains("<fl>")) {
            int entryStartNodeFl = currentXML.indexOf("<fl>");
            int entryEndNodeFl = currentXML.indexOf("</fl>");
            String fl = currentXML.substring(entryStartNodeFl + 4, entryEndNodeFl);
            //assign fl to xml_entry class
            currentXML = currentXML.substring(entryEndNodeFl + 5, currentXML.length());
            Toast.makeText(context.getApplicationContext(), "fl: " + fl, Toast.LENGTH_LONG).show();
        }

        //getting the def in the entry
        if (currentXML.contains("<def>")) {
            int entryStartNodeDef = currentXML.indexOf("<def>");
            int entryEndNodeDef = currentXML.indexOf("</def>");
            String def = currentXML.substring(entryStartNodeDef + 5, entryEndNodeDef);
            //assign def to xml_entry class
            Toast.makeText(context.getApplicationContext(), "def: " + def, Toast.LENGTH_LONG).show();
            boolean endSensB = false;
            currentXML = currentXML.substring(entryStartNodeDef + 5, currentXML.length());

            //getting the sens' in the entry
            while (!endSensB) {
                if (currentXML.contains("<sensb>")){
                    //getting all instances of sensb
                    String[] sensBArray = currentXML.split("<sensb>");

                    for (int i = 0; i < sensBArray.length; i++) {
                        if (sensBArray[i].equals("")) {

                        } else {
                            //getting all instances of sens
                            String[] sensArray = sensBArray[i].split("<sens>");

                            for (int j = 0; j < sensArray.length; j++) {
                                if (sensArray[j].equals("")) {

                                } else {
                                    //getting the dt in the entry
                                    if (sensArray[j].contains("<dt>")) {
                                        if (sensArray[j].contains("<sx>")) {
                                            int entryStartNodeSx = sensArray[j].indexOf("<sx>");
                                            int entryEndNodeSx = sensArray[j].indexOf("</sx>");
                                            String sx = sensArray[j].substring(entryStartNodeSx + 4, entryEndNodeSx);
                                            //assign sx to xml_entry class
                                            Toast.makeText(context.getApplicationContext(), "sx: " + sx, Toast.LENGTH_LONG).show();
                                        } else {
                                            int entryStartNodeDt = sensArray[j].indexOf("<dt>");
                                            int entryEndNodeDt = sensArray[j].indexOf("</dt>");
                                            String dt = sensArray[j].substring(entryStartNodeDt + 4, entryEndNodeDt);
                                            //assign dt to xml_entry class
                                            Toast.makeText(context.getApplicationContext(), "dt: " + dt, Toast.LENGTH_LONG).show();
                                        }

                                    }
                                }

                                if (j == (sensArray.length - 1)) {
                                    endSensB = true;
                                }
                            }

                        }

                        if (i == (sensBArray.length - 1)) {
                            endSensB = true;
                        }
                    }

                } else {
                    endSensB = true;
                }
            }
            /*currentXML = currentXML.substring(entryEndNodeDef + 6, currentXML.length());
            Toast.makeText(context.getApplicationContext(), "def: " + def, Toast.LENGTH_LONG).show();*/
        }

        /*
        //getting the dt in the entry
        if (currentXML.contains("<dt>")) {
            if (currentXML.contains("<sx>")){
                int entryStartNodeSx = currentXML.indexOf("<sx>");
                int entryEndNodeSx = currentXML.indexOf("</sx>");
                String sx = currentXML.substring(entryStartNodeSx + 4, entryEndNodeSx);
                //assign sx to xml_entry class
                int endOfEntry = currentXML.indexOf("</def>");
                currentXML = currentXML.substring(endOfEntry + 6, currentXML.length());
                Toast.makeText(context.getApplicationContext(), "sx: " + sx, Toast.LENGTH_LONG).show();
            } else {
                int entryStartNodeDt = currentXML.indexOf("<dt>");
                int entryEndNodeDt = currentXML.indexOf("</dt>");
                String dt = currentXML.substring(entryStartNodeDt + 4, entryEndNodeDt);
                //assign dt to xml_entry class
                int endOfEntry = currentXML.indexOf("</def>");
                currentXML = currentXML.substring(endOfEntry + 6, currentXML.length());
                Toast.makeText(context.getApplicationContext(), "dt: " + dt, Toast.LENGTH_LONG).show();
            }

        }*/
    }

    private String removeRootNode() {
        String noNode1 = xmlFromServer.replace("<entry_list version=\"1.0\">", "");
        String noNode2 = noNode1.replace("</entry_list>", "");
        return noNode2;
    }
}
