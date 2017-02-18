/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.json;

import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.map.annotate.JsonSerialize;

@JsonSerialize
public class JSONFestivalWrapper {
    @JsonProperty(value = "Festival")
    private JSONFestival festival;

    public JSONFestival getFestival() {
        return festival;
    }

    public void setFestival(JSONFestival festival) {
        this.festival = festival;
    }
    
    
}
