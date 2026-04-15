package mapper;

import model.Device;
import model.DeviceData;

import java.math.BigDecimal;
import java.util.Map;

import static java.lang.Double.parseDouble;

public class DeviceMapper {

    public Device mapper(Map<String, String> dataMap){

        return new Device()
                .setName(dataMap.get("name"))
                .setDeviceData(getDeviceData(dataMap));
    }

    public DeviceData getDeviceData(Map<String, String> dataMap){
            return new DeviceData().setPrice(parseDouble(dataMap.get("price")))
                    .setYear(Integer.parseInt(dataMap.get("year")));

    }
}
