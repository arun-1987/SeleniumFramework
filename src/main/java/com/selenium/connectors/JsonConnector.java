package com.selenium.connectors;

import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

import com.selenium.ui.helper.LoggerHelper;
import org.apache.logging.log4j.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.testng.Assert;
import org.testng.annotations.DataProvider;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import com.selenium.ui.helper.ResourceHelper;

public class JsonConnector {
    public static String getConfig(String requestparam) {
        try {
            Object obj = new JSONParser().parse(new FileReader(ResourceHelper.getResourceHelper("/src/main/resources/config.json")));
            JSONObject jo = (JSONObject) obj;
            requestparam = (String) jo.get(requestparam);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return requestparam;
    }


    /**
     * Function to get array of data from test data
     *
     * @param tcName
     * @param requestparam
     * @return
     */
    public static JSONArray getArrayData(String tcName, String requestparam) {
        JSONArray jsonArray = null;
        try {
            String dataFile = ResourceHelper.getResourceHelper("/src/main/resources/testdata/testdata.json");
            JSONArray testData = (JSONArray) extractData_JSON(dataFile).get(tcName);
            List<JSONObject> testDataList = new ArrayList<JSONObject>();
            for (int i = 0; i < testData.size(); i++) {
                testDataList.add((JSONObject) testData.get(i));
            }
            jsonArray = (JSONArray) testDataList.get(0).get(requestparam);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return jsonArray;
    }

    /**
     * extractData_JSON - method to extract JSON data from a file
     *
     * @param file (including path)
     * @return JSONObject
     * @throws Exception
     */
    public static JSONObject extractData_JSON(String file) throws Exception {
        FileReader reader = new FileReader(file);
        JSONParser jsonParser = new JSONParser();
        return (JSONObject) jsonParser.parse(reader);
    }
}
