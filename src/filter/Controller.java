package filter;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;

public class Controller {

    @FXML
    private TextArea txt_beforeFilterJson;

    @FXML
    private Button btn_filterJson;

    @FXML
    private TextArea txt_afterFilterJson;

    @FXML
    void filterJson(ActionEvent event) {
        String json = this.txt_beforeFilterJson.getText();
        JSONObject obj = JSON.parseObject(json, JSONObject.class);
        obj = filterNullValue(obj);
        this.txt_afterFilterJson.setText(JSON.toJSONString(obj));
    }

    private static JSONObject filterNullValue(JSONObject obj) {
        JSONObject newobj = (JSONObject)obj.clone();
        for (Map.Entry<String, Object> entry : obj.entrySet()) {
            if(entry.getValue() == null || (entry.getValue() != null && entry.getValue().toString().trim().equals(""))){
                newobj.remove(entry.getKey());
            }else if(entry.getValue() instanceof JSONArray){
                JSONArray array = (JSONArray)entry.getValue();
                JSONArray newarray = new JSONArray();
                for (Object o : array) {
                    JSONObject jsonObject = (JSONObject)o;
                    jsonObject = filterNullValue(jsonObject);
                    newarray.add(jsonObject);
                }
                newobj.put(entry.getKey(),newarray);
            }
        }
        return newobj;
    }

}
