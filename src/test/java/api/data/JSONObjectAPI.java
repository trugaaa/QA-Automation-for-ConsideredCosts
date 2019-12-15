package api.data;

import org.json.JSONException;
import org.json.JSONObject;

import javax.ws.rs.core.MultivaluedHashMap;
import javax.ws.rs.core.MultivaluedMap;
import java.util.HashMap;
import java.util.Map;

public class JSONObjectAPI extends JSONObject {

    public JSONObject putAccept( String value) throws JSONException {
        put("accept",value);
        return this;
    }
    public JSONObject putRequest( String value) throws JSONException {
        put("request",value);
        return this;
    }
    public JSONObject putEndpoint( String value) throws JSONException {
        put("endpoint",value);
        return this;
    }
    public JSONObject putHeaders( HashMap<String,Object> myHeaders) throws JSONException {
        put("headers",myHeaders);
        return this;
    }

    public String getRequest() throws JSONException {
        Object object = this.get("request");
        if (object instanceof String) {
            return (String)object;
        } else {
            throw new JSONException("JSONObject[" + quote("request") + "] not a string.");
        }
    }

    public String getAccept() throws JSONException {
        Object object = this.get("accept");
        if (object instanceof String) {
            return (String)object;
        } else {
            throw new JSONException("JSONObject[" + quote("accept") + "] not a string.");
        }
    }
    public String getEndpoint() throws JSONException {
        Object object = this.get("endpoint");
        if (object instanceof String) {
            return (String)object;
        } else {
            throw new JSONException("JSONObject[" + quote("endpoint") + "] not a string.");
        }
    }

    public Map<String, Object> getHeaders() throws JSONException {
        JSONObject object = this.getJSONObject("headers");
        System.out.println(object.getClass());
        if (object.toMap() instanceof HashMap) {
            return object.toMap();
        } else {
            throw new JSONException("JSONObject[" + quote("headers") + "] not a Map.");
        }
    }

    public MultivaluedMap<String, Object> getHeadersMultiMap() throws JSONException {
        JSONObject object = this.getJSONObject("headers");
        MultivaluedMap<String,Object> multivaluedMap=new MultivaluedHashMap<>();

        object.toMap().forEach((k,v) -> {
            multivaluedMap.add(k, v);
        });
        return multivaluedMap;
    }
}
