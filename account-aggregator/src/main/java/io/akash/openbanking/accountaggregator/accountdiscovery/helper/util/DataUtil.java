package io.akash.openbanking.accountaggregator.accountdiscovery.helper.util;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import io.akash.openbanking.accountaggregator.accountdiscovery.helper.dto.v1.FITypeDTO;
import io.akash.openbanking.accountaggregator.accountdiscovery.helper.enumeration.FITypeNameEnum;


/**
 * Purpose:- 
 * This class helps with generic data related operations like 
 * data generation, data manipulation, data transformation etc.
 * 
 * 
 * @author 
 * @version 1.0
 * @since   2019-03-27
 */

@Component
public class DataUtil {

    /**
     *
     * @return transaction id
     */
    public static String getTransactionId()
    {
        /*int minimum=5;
        int maximum=99999999;
        String date = new SimpleDateFormat("yyyyMMdd").format(new java.util.Date());
        String randomNum = String.valueOf(minimum + (int)(Math.random() * maximum));*/

        Instant instant=Instant.now();
        String transcationId=instant.toString();
        return transcationId;

    }

	public static List<FITypeDTO> getFiTypes()
	   {
	       List<FITypeDTO> fiTypeDTOList=new ArrayList<FITypeDTO>();
	
	           for (FITypeNameEnum fitype : FITypeNameEnum.values()) {
	               FITypeDTO fiTypeDTO=new FITypeDTO();
	               fiTypeDTO.setName(fitype);
	               fiTypeDTO.setValue(fitype.value());
	               fiTypeDTOList.add(fiTypeDTO);
	           }
	        return fiTypeDTOList;
	   }
}
