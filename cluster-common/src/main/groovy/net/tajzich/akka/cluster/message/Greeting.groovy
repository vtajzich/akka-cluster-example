package net.tajzich.akka.cluster.message

import groovy.transform.Immutable
import groovy.transform.ToString

/**
 * Created by vtajzich on 06/02/14.
 */
@Immutable
@ToString
class Greeting implements Message {

    private static final long serialVersionUID = 5099256720671654586L ;

    String value
}
