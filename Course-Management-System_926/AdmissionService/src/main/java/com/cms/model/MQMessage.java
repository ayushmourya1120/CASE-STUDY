package com.cms.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

@AllArgsConstructor
@Data
public class MQMessage implements Serializable {

    String toSendRegistrationId;
    String toSendCourseId;
    String toSendAssociateId;
    String toSendDate;
    String associateEmail;
    String associateName;

}
