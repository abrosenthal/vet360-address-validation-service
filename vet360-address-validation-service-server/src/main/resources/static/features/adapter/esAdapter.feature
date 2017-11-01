@Adapter
Feature: ES Member Service Adapter Listener/Client

    Rules:
      -

    Background: Define the standard HTTP status code and Change Log consume status response process (fields and values) that will be returned
    Given I have defined the structure of the standard HTTP status code response process as
    |  httpStatusCode  |  shortDescription                 | Retry |
    |  100             |  Continue                         | No    |
    |  101             |  Switching Protocols              | No    |
    |  200             |  OK                               | No    |
    |  201             |  Created                          | No    |
    |  202             |  Accepted                         | No    |
    |  203             |  Non-Authoritative Information    | No    |
    |  204             |  No Content                       | No    |
    |  205             |  Reset Content                    | No    |
    |  206             |  Partial Content                  | No    |
    |  300             |  Multiple Choices                 | Yes   |
    |  301             |  Moved Permanently                | Yes   |
    |  302             |  Found                            | Yes   |
    |  303             |  See Other                        | Yes   |
    |  304             |  Not Modified                     | Yes   |
    |  307             |  Temporary Redirect               | Yes   |
    |  308             |  Permanent Redirect               | Yes   |
    |  400             |  Bad Request                      | No    |
    |  401             |  Unauthorized                     | No    |
    |  403             |  Forbidden                        | Yes   |
    |  404             |  Not Found                        | Yes   |
    |  405             |  Method Not Allowed               | Yes   |
    |  406             |  Not Acceptable                   | Yes   |
    |  407             |  Proxy Authentication Required    | Yes   |
    |  408             |  Request Timeout                  | Yes   |
    |  409             |  Conflict                         | Yes   |
    |  410             |  Gone                             | Yes   |
    |  411             |  Length Required                  | Yes   |
    |  412             |  Precondition Failed              | Yes   |
    |  413             |  Payload Too Large                | Yes   |
    |  414             |  URI Too Long                     | Yes   |
    |  415             |  Unsupported Media Type           | Yes   |
    |  416             |  Requested Range Not Satisfiable  | Yes   |
    |  417             |  Expectation Failed               | Yes   |
    |  421             |  Misdirected Request              | Yes   |
    |  426             |  Upgrade Required                 | Yes   |
    |  428             |  Precondition Required            | Yes   |
    |  429             |  Too Many Requests                | Yes   |
    |  431             |  Request Header Fields Too Large  | Yes   |
    |  451             |  Unavailable For Legal Reasons    | Yes   |
    |  500             |  Internal Server Error            | Yes   |
    |  501             |  Not Implemented                  | Yes   |
    |  502             |  Bad Gateway                      | Yes   |
    |  503             |  Service Unavailable              | Yes   |
    |  504             |  Gateway Timeout                  | Yes   |
    |  505             |  HTTP Version Not Supported       | Yes   |
    |  506             |  Variant Also Negotiates          | Yes   |
    |  507             |  Insufficient Storage             | Yes   |
    |  510             |  Not Entended                     | Yes   |
    |  511             |  Network Authentication Required  | Yes   |
    # referenced at https://developer.mozilla.org/en-US/docs/Web/HTTP/Status which uses
    # section 10 of RFC 2616

    Given I have defined the structure of the standard Change Log consume status response process as
    | changeLogConsumeStatus       | statusDescription                 |
    | FAILED                       | failed                            |
    | FAILED_RETRY                 | failed retry                      |
    | RECEIVED                     | received                          |
    | COMPLETED_SUCCESS            | completed and successful          |
    | COMPLETED_NO_CHANGE_DETECTED | completed and no changes detected |
    | NOOP                         | no operation                      |
    # referenced at mdm.cuf.core.api.async.ChangeLogConsumeStatus. TBD for finalized list of statuses

    @Listener
    Scenario: Person Change Log Listener consumes Change Log Instruction
      Given the listener receives a Person CUF Instruction from the Change Log Queue
      When it consumes the CUF Instruction
      Then it forwards the CUF Instruction to our Person REST client

    @Listener
    Scenario: Non-person (i.e. Telephone, Email, Address) Change Log Listener consumes Change Log Instruction
      Given the listener receives a CUF Instruction from the Change Log Queue
      When it consumes the CUF Instruction
      And adapts the message into the proper format for a Person Bio
      And it contains a list of "addresses" with the below values
      | addressId | cityName | countryCode | countryName | createDate | originatingSourceSystem | sourceDate | sourceSystem | sourceSystemUser | stateCode | street1 | street2 | txAuditId | updateDate | versionHash | vet360Id |
      And it contains a value for "createDate" for the Person Bio
      And it contains a list of "emails" with the below values
      | confirmationDate | createDate | effectiveEndDate | effectiveStartDate | emailAddressText | emailId | emailPermInd | emailStatusCode | originatingSourceSystem | sourceDate | sourceSystem | sourceSystemUser | txAuditId | updateDate | versionHash | vet360Id |
      And it contains a list of "telephones" with the below values
      | areaCode | confirmationDate | connectionStatusCode | countryCode | createDate | effectiveEndDate | effectiveStartDate | internationalIndicator | originatingSourceSystem | phoneNumber | phoneNumberExt | phoneType | sourceDate | sourceSystem | sourceSystemUser | textMessageCapableInd | textMessagePermInd | ttyInd | txAuditId | updateDate | versionHash | vet360Id | voiceMailAcceptableInd |
      And it contains values for "txAuditId", "updateDate", "versionHash", & "vet360Id" for the Person Bio
      And gets the IEN from the Supplemental Bios
      | 000000123456789V123456000000 |
      Then it forwards the Person Bio and the IEN to our Person REST client
      # Person Bio values referenced from Swagger UI

    @Client
    Scenario Outline: Rest Client makes a request to a RestEndpoint for the BIO Interface successful
      Given the listener has adapted the CUF Instruction to an outbound "<bioType>"
      When the client makes a request to the "<restEndpoint>" to the "<bioType>" interface
      Then the Full BIO is received by the Internal Inbound Vet360 update Queue
      And 200 Ok HTTP response code is received
      # And "RECEIVED" consume status sent to CI Hub
      Examples: RestEndpoint and BIO_TYPE
      | restEndpoint                                                    | bioType  |
      | http://vaausappesr832.aac.va.gov:7101/contactinfo-ws/contactinf | Person   |

    @Client @Error @Retry
    Scenario Outline: Rest Client makes a request to a RestEndpoint for the BIO Interface Not Found HTTP response
    Given the listener has adapted the CUF Instruction to an outbound "<bioType>"
    When the client makes a request to the "<restEndpoint>" to the "<bioType>" interface
    Then the system receives a 404 Not Found error HTTP response code
    And the message is placed on a retry queue
    And attempts an alloted amount of retries for a specified interval
    # And "RECEIVED" consume status sent to CI Hub
    Examples: RestEndpoint and BIO_TYPE
    | restEndpoint                                                    | bioType  |
    | http://vaausappesr832.aac.va.gov:7101/contactinfo-ws/contactinf | Person   |

    @Client @Error @Retry
    Scenario Outline: Rest Client makes a request to a RestEndpoint and needs to retry request
      Given the listener has adapted the CUF Instruction to an outbound "<bioType>"
      When the client makes a request to the "<restEndpoint>" to the "<bioType>" interface
      Then the system receives a 500 Internal Server error HTTP response code
      And the message is placed on a retry queue
      And attempts an alloted amount of retries for a specified interval
      # And "FAILED_RETRY" consume status sent to CI Hub
      Examples: RestEndpoint and BIO_TYPE
      | restEndpoint                                                    | bioType  |
      | http://vaausappesr832.aac.va.gov:7101/contactinfo-ws/contactinf | Person   |

    @Client @Error @Retry
    Scenario Outline: Rest Client retries request and fails
      Given the listener has attempted the alloted amount of retries
      When the client makes a request to the "<restEndpoint>" to the "<bioType>" interface
      Then the system receives a 408 Request Timeout HTTP response code
      And the message is placed on a retry queue
      And attempts an alloted amount of retries for a specified interval
    #  And "FAILED_RETRY" consume status sent to CI Hub
      Examples: RestEndpoint and BIO_TYPE
      | restEndpoint                                                    | bioType  |
      | http://vaausappesr832.aac.va.gov:7101/contactinfo-ws/contactinf | Person   |

    @Client @Retry
    Scenario Outline: Rest Client retries request and succeeds
      Given the listener attempts a retry for the request
      When the client makes the request to the "<restEndpoint>" to the "<bioType>" interface
      Then the system receives a 200 Ok HTTP response code
      And "RECEIVED" consume status sent to CI Hub
      Examples: RestEndpoint and BIO_TYPE
      | restEndpoint                                                    | bioType  |
      | http://vaausappesr832.aac.va.gov:7101/contactinfo-ws/contactinf | Person   |

    @Client @Error
    Scenario: Rest Client makes a request to the RestEndpoint with the wrong BIO
      Given the consumer has adapted the CUF Instruction to an outbound "Phone BIO"
      | txAuditId | vet360Id | sourceSystem | internationalIndicator | countryCode | areaCode | phoneNumber | phoneType | sourceDate       | effectiveStartDate | effectiveEndDate | phoneNumberExt | voiceMailAcceptableInd | ttyInd | textMessageCapableInd | textMessagePermInd | connectionStatusCode | confirmationDate |
      |           | 1        | VETSGOV      | False                  | 1           | 703      | 3234567     | HOME      | Today - 3 months | Today - 3 months   |                  | 444            | true                   |        | false                 |                    | NO_KNOWN_PROBLEM     | Today - 3 months |
      When the client makes a request to the "<restEndpoint>" to the Person Bio Interface
      Then the system receives a TBD error response code (Incorrect_Bio_Type)
      And a 400 Bad Request/Bad JSON HTTP response code
      # And "FAILED" consume status sent to CI Hub
      | restEndpoint                                                    |
      | http://vaausappesr832.aac.va.gov:7101/contactinfo-ws/contactinf |

    @Client @Error
    Scenario Outline: Rest Client makes a request to a RestEndpoinT but falls into unrecoverable error
      Given the listener has adapted the CUF Instruction to an outbound "<bioType>"
      When the client makes a request to the "<restEndpoint>" to the "<bioType>" interface
      Then the system receives a TBD error response code (JSR 303?)
      And places the error on a dead letter queue
      # And "FAILED" consume status sent to CI Hub
      Examples: RestEndpoint and BIO_TYPE
      | restEndpoint                                                    | bioType  |
      | http://vaausappesr832.aac.va.gov:7101/contactinfo-ws/contactinf | Person   |
