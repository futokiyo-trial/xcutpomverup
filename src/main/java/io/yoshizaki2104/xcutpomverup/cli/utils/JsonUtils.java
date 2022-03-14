package io.yoshizaki2104.xcutpomverup.cli.utils;

import java.io.IOException;

//import org.apache.commons.lang3.StringUtils;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonMappingException;



/**
 *
 */
public class JsonUtils {
	
	//private static Logger logger = LoggerFactory.getLogger(JsonUtils.class);

    private static ObjectMapper objMapper = null;


    // ObjectMapperは都度生成すると重いため、静的に1度だけ生成する
    static{
        try{
            objMapper = new ObjectMapper();
        } catch(Throwable th){
            th.printStackTrace();
        }
    }

    /** コンストラクタ禁止 */
    private JsonUtils(){}


    public static <T> T parse(Class<T> dtoClazz, String json) throws IOException {
         return (T) objMapper.readValue(json, dtoClazz);
    }
    
 
}
