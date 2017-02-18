/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.json;

import java.util.Date;
import java.util.List;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.map.annotate.JsonSerialize;

@JsonSerialize
public class JSONFestival {
    
    @JsonProperty(value = "Name")
    private String name;
    
    @JsonProperty(value = "Place")
    private String place;
    
    @JsonProperty(value = "StartDate")
    private String startDate;
    
    @JsonProperty(value = "EndDate")
    private String endDate;
    
    @JsonProperty(value = "Tickets")
    private List<String> tickets;
    
    @JsonProperty(value = "PerformersList")
    private List<JSONPerformer> performers;
    
    @JsonProperty(value = "SocialNetworks")
    private List<JSONSocialNetwork> socialNetworks;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public List<String> getTickets() {
        return tickets;
    }

    public void setTickets(List<String> tickets) {
        this.tickets = tickets;
    }

    public List<JSONPerformer> getPerformers() {
        return performers;
    }

    public void setPerformers(List<JSONPerformer> performers) {
        this.performers = performers;
    }

    public List<JSONSocialNetwork> getSocialNetworks() {
        return socialNetworks;
    }

    public void setSocialNetworks(List<JSONSocialNetwork> socialNetworks) {
        this.socialNetworks = socialNetworks;
    }
 
    
    
}
