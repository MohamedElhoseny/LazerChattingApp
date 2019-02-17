package com.jets.LasserChatServer.models.dao.daoInterfaces;

import java.util.Map;

public interface DataMining
{
	Map<String, Integer> showGenderStatistics();
	Map<String, Integer> showCountryStatistics();
	Map<String, Integer> showUserAvailability();
}
