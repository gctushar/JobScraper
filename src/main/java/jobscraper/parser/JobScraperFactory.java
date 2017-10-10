/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jobscraper.parser;

import jobscraper.common.Utils;

/**
 *
 * @author tushar
 */
public class JobScraperFactory {
    
    public JobScraperInterface getJobScraper(String domainName){
        if(domainName == null || domainName.isEmpty()){
            return null;
        }else if(domainName.equalsIgnoreCase("dice.com")){
            return new DiceParser();
        }else if(domainName.equalsIgnoreCase("monster.com")){
            return new MonstarParser();
        }
        return null;
    }
    
}
