package com.flab.fkream.interestItemCount;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "interestItemCount")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class InterestItemCount {

    @Id
    private ObjectId id;
    @Indexed(unique = true)
    private Long itemId;
    private int count;


    public InterestItemCount(Long itemId, int count) {
        this.itemId = itemId;
        this.count = count;
    }

    public void increaseCount() {
        this.count++;
    }

    public void decreaseCount() { this.count--;}
}
