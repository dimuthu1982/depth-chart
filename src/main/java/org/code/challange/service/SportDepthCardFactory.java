package org.code.challange.service;

import java.util.HashMap;
import java.util.Map;

import org.code.challange.card.RankingChart;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class SportDepthCardFactory
{
    private static final Logger LOGGER= LoggerFactory.getLogger(SportDepthCardFactory.class);

    private Map<String, RankingChart> sportDepthCardHolder;

    @Autowired
    public SportDepthCardFactory(@Value("#{${card.sports}}") Map<String, Integer> sportsDepthConfigMap)
    {
        this.sportDepthCardHolder = new HashMap();

        for (Map.Entry<String, Integer> config : sportsDepthConfigMap.entrySet())
        {
            sportDepthCardHolder.put(config.getKey().toLowerCase(), new RankingChart(config.getKey(), config.getValue()));
            LOGGER.info("Instantiating sport {}", config.getKey());
        }

    }

    public RankingChart getDepthCard(String sport)
    {
        return sportDepthCardHolder.get(sport.toLowerCase());
    }
}
