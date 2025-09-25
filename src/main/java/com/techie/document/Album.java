package com.techie.document;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document // ✅ No invalid collation
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Album {

    @Id
    @JsonProperty("_id")
    private String id;

    private String name;
    private String desc;
    private String bgColour;
    private String imageUrl;
}
