package org.mashad.jbsbe.iso;

import lombok.Builder;
import lombok.Data;
import org.mashad.jbsbe.annotation.Iso8583;
import org.mashad.jbsbe.annotation.IsoField;

import java.util.Date;

@Iso8583(type=0x200)
@Builder
@Data
public class PurchaseRequestPrivate {
	@IsoField(index=10)
	private Date date;
	@IsoField(index=4)
	private Long amount;
	@IsoField(index=11)
	private Integer stan;
	@IsoField(index=35)
	private String cardNumber;
	
}
