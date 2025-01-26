package com.yashdevs.dataScraper.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yashdevs.dataScraper.constant.NumericConstant;
import com.yashdevs.dataScraper.entity.MFAPIResponseDTO;
import com.yashdevs.dataScraper.entity.MFAPIResponseDTO.NAVObject;

@Service
public class JsonParsor {

	public static MFAPIResponseDTO mfApiResponseDTOParseJson(String json,
			TypeReference<MFAPIResponseDTO> typeReference) {
		MFAPIResponseDTO dto;
		try {
			ObjectMapper objectMapper = new ObjectMapper();
			dto = objectMapper.readValue(json, typeReference);
			List<NAVObject> navData = dto.getNAVObject();
			if (navData != null) {
				int size = navData.size() > NumericConstant.PAST_TRADE_DAYS? NumericConstant.PAST_TRADE_DAYS: navData.size();
				dto.setNAVObject(navData.subList(0, size)); // Limit the navData list to last 700 days
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

		return dto;
	}
}
