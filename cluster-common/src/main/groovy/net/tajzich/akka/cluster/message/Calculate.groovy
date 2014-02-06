package net.tajzich.akka.cluster.message

import groovy.transform.Immutable
import groovy.transform.ToString

/**
 * Created by vtajzich on 29/01/14.
 */
@Immutable
@ToString
class Calculate implements Message {

    private static final long serialVersionUID = -5232693437190251787L;

    BigDecimal left
    BigDecimal right

}
