package com.carbonchain.server;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.SimpleDateFormat;

import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.ext.Provider;
import org.glassfish.jersey.jackson.internal.jackson.jaxrs.json.JacksonJaxbJsonProvider;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;

@Provider
@Produces(MediaType.APPLICATION_JSON)
public class CustomMapperProvider extends JacksonJaxbJsonProvider {
	private static final String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
    private static ObjectMapper mapper = new ObjectMapper();

    static {
    	SimpleModule bigIntegerModule = new SimpleModule();
    	bigIntegerModule.addSerializer(BigInteger.class, new ToStringSerializer());
    	mapper.registerModule(bigIntegerModule);
    	
    	SimpleModule bigDecimalModule = new SimpleModule();
    	bigDecimalModule.addSerializer(BigDecimal.class, new ToStringSerializer());
    	mapper.registerModule(bigDecimalModule);
    	
    	//mapper.configure(JsonGenerator.Feature.WRITE_NUMBERS_AS_STRINGS,true);
    	//mapper.enable(JsonGenerator.Feature.WRITE_BIGDECIMAL_AS_PLAIN);
    	//mapper.configure(SerializationFeature.WRITE_DATE_TIMESTAMPS_AS_NANOSECONDS, true);
    	mapper.enable(SerializationFeature.INDENT_OUTPUT);		//缩放排版
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        //mapper.setSerializationInclusion(Include.NON_NULL);	//不序列化为空的属性
        
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
        mapper.setDateFormat(sdf);
        mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
     }

    public CustomMapperProvider() {
        super();
        setMapper(mapper);
    }
}