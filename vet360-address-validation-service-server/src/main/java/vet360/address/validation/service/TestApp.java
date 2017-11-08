package vet360.address.validation.service;

import org.springframework.stereotype.Component;

import mdm.cuf.core.api.async.CufChangeLogInstruction;
import mdm.cuf.core.server.processor.async.changelog.ChangeLogConsumerHandler;
import mdm.cuf.core.server.processor.async.changelog.ChangeLogHandlerResponse;

@Component
public class TestApp implements ChangeLogConsumerHandler {

    @Override
    public ChangeLogHandlerResponse consume(CufChangeLogInstruction arg0) {
        throw new UnsupportedOperationException();
    }

    
}
