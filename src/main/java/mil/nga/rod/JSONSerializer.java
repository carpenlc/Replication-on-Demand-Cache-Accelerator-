package mil.nga.rod;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.TimeZone;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;

import mil.nga.rod.model.QueryRequestAccelerator;

/**
 * This class was created for testing purposes.  It will "marshall"
 * an input Object into it's String-based JSON equivalent.
 * 
 * @author L. Craig Carpenter
 */
public class JSONSerializer {

    /**
     * Set up the LogBack system for use throughout the class
     */        
    private static final Logger LOGGER = LoggerFactory.getLogger(
            JSONSerializer.class);
    
    /** 
     * DateFormat object used when serializing/deserializing dates.  This 
     * overrides the default behavior which depends on the type of date being
     * serialized/deserialized.
     * 
     * Updated: The information coming out of the database is a date, not a 
     * date/time object.  The time component is not included.  As such JAX-B
     * throws errors trying to deserialize the time part.  Updated to exclude
     * time.
     */
    private static final DateFormat dateFormatter = 
            new SimpleDateFormat("yyyy-MM-dd");
    
    static {
        dateFormatter.setTimeZone(TimeZone.getTimeZone("GMT"));
    }
    
    /**
     * Accessor method for the singleton instance of the 
     * JSONSerializer class.
     * 
     * @return The singleton instance of the JSONSerializer .
     * class.
     */
    public static JSONSerializer getInstance() {
        return JSONSerializerHolder.getSingleton();
    }    
    
    /**
     * Method used to deserialize a JSON array into an object or type 
     * <code>mil.nga.rod.model.QueryRequestAccelerator</code>
     * 
     * @param json The String in JSON format.
     * @return A <code>mil.nga.rod.model.QueryRequestAccelerator</code> object. 
     * Null if any exceptions were encountered while deserializing the String.
     */
    public QueryRequestAccelerator deserializeToQueryRequestAccelerator(String json) {
        
        QueryRequestAccelerator deserialized = null;
        
        try {
            if (json != null) {
                
                ObjectMapper mapper = new ObjectMapper();
                mapper.setDateFormat(dateFormatter);
                deserialized = mapper.readValue(
                        json, 
                        QueryRequestAccelerator.class);
                
            }
        }
        catch (JsonMappingException jme) {
            LOGGER.error("Unexpected JsonMappingException encountered "
                    + "while attempting to deserialize the input "
                    + "JSON to a List of String objects.  Exception "
                    + "message [ "
                    + jme.getMessage()
                    + " ].");
        }
        catch (JsonParseException jpe) {
            LOGGER.error("Unexpected JsonParseException encountered "
                    + "while attempting to deserialize the input "
                    + "JSON into a List of String objects.  Exception "
                    + "message [ "
                    + jpe.getMessage()
                    + " ].");
        }
        catch (IOException ioe) {
            LOGGER.error("Unexpected IOException encountered "
                    + "while attempting to deserialize the input "
                    + "JSON into a List of String objects.  Exception "
                    + "message [ "
                    + ioe.getMessage()
                    + " ].");
        }
        return deserialized;
    }
    
    /**
     * Method used to deserialize a JSON array into a List of String objects.
     * 
     * @param json The String in JSON format.
     * @return A <code>java.util.List</code> object containing String objects.
     * Null if any exceptions were encountered while deserializing the String.
     */
    public List<String> deserializeToStringList(String json) {
        
        List<String> deserialized = null;
        
        try {
            if (json != null) {
                
                ObjectMapper mapper = new ObjectMapper();
                mapper.setDateFormat(dateFormatter);
                CollectionType outputType = mapper.getTypeFactory()
                        .constructCollectionType(List.class, String.class);
                deserialized = mapper.readValue(json, outputType);
                
            }
        }
        catch (JsonMappingException jme) {
            LOGGER.error("Unexpected JsonMappingException encountered "
                    + "while attempting to deserialize the input "
                    + "JSON to a List of String objects.  Exception "
                    + "message [ "
                    + jme.getMessage()
                    + " ].");
        }
        catch (JsonParseException jpe) {
            LOGGER.error("Unexpected JsonParseException encountered "
                    + "while attempting to deserialize the input "
                    + "JSON into a List of String objects.  Exception "
                    + "message [ "
                    + jpe.getMessage()
                    + " ].");
        }
        catch (IOException ioe) {
            LOGGER.error("Unexpected IOException encountered "
                    + "while attempting to deserialize the input "
                    + "JSON into a List of String objects.  Exception "
                    + "message [ "
                    + ioe.getMessage()
                    + " ].");
        }
        return deserialized;
    }
    
    /**
     * Convert the input object into JSON format. 
     * 
     * @param obj A populated object.
     * @return A JSON String representation of the input Object.
     */
    public String serialize(Object obj) {
        
        String json = "null";
        
        if (obj != null) {
            try {
                ObjectMapper mapper = new ObjectMapper();
                mapper.setDateFormat(dateFormatter);
                json = mapper.writeValueAsString(obj);
            }
            catch (JsonProcessingException jpe) {
                LOGGER.error("Unexpected JsonProcessingException encountered "
                        + "while attempting to marshall the input "
                        + "object to JSON.  Exception message [ "
                        + jpe.getMessage()
                        + " ].");
            }
        }
        else {
            LOGGER.warn("Input object is null.  Unable to "
                    + "marshall the object to JSON.");
        }
        return json;
    }
    
    /**
     * Static inner class used to construct the Singleton object.  This class
     * exploits the fact that classes are not loaded until they are referenced
     * therefore enforcing thread safety without the performance hit imposed
     * by the <code>synchronized</code> keyword.
     * 
     * @author L. Craig Carpenter
     */
    public static class JSONSerializerHolder {
        
        /**
         * Reference to the Singleton instance of the 
         * JSONSerializer
         */
        private static JSONSerializer _instance = 
                new JSONSerializer();
    
        /**
         * Accessor method for the singleton instance of the 
         * JSONSerializer.
         * @return The Singleton instance of the 
         * JSONSerializer.
         */
        public static JSONSerializer getSingleton() {
            return _instance;
        }
        
    }
}
