package co.sofka.data.transaction;

import co.sofka.DinError;
import co.sofka.DinHeader;
import co.sofka.ResponseMs;

public class ResponseTransactionMs extends ResponseMs<TransactionDto> {

    public ResponseTransactionMs(TransactionDto customerDto) {
        super(new DinHeader(), customerDto, defaultDinError());
    }

    public ResponseTransactionMs(DinHeader dinHeader, TransactionDto transactionDto) {
        super(dinHeader != null ? dinHeader : new DinHeader(), transactionDto, defaultDinError());
    }


    public ResponseTransactionMs(DinError dinError) {
        super(new DinHeader(), null, dinError != null ? dinError : defaultDinError());
    }

    public ResponseTransactionMs(TransactionDto transactionDto, DinError dinError) {
        super(new DinHeader(), transactionDto, dinError != null ? dinError : defaultDinError());
    }

    public ResponseTransactionMs(DinHeader dinHeader, TransactionDto transactionDto, DinError dinError) {
        super(dinHeader != null ? dinHeader : new DinHeader(), transactionDto, dinError != null ? dinError : defaultDinError());
    }

    private static DinError defaultDinError() {
        return new DinError();
    }
}
