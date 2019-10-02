import javax.naming.InsufficientResourcesException;

public class TransferMoney {
    private static final Object tieLock = new Object();

    public void seqTransferMoney(final Account fromAcct,
                                 final Account toAcc,
                                 final DollarAmount amount)
            throws InsufficientFundsException {

        class Helper {
            public void transfer() throws InsufficientFundsException {
                if (fromAcct.getBalance().compareTo(amount) < 0) {
                    throw new InsufficientFundsException();
                } else {
                    fromAcct.debit(amount);
                    toAcc.credit(amount);
                }
            }
        }

        int fromHash = System.identityHashCode(fromAcct);
        int toHash = System.identityHashCode(toAcc);

        if (fromHash < toHash) {
            synchronized (fromAcct) {
                synchronized (toAcc) {
                    new Helper().transfer();
                }
            }
        } else if (fromHash > toHash) {
            synchronized (toAcc) {
                synchronized (fromAcct) {
                    new Helper().transfer();
                }
            }
        } else {
            synchronized (tieLock) {
                synchronized (fromAcct) {
                    synchronized (toAcc) {
                        new Helper().transfer();
                    }
                }
            }
        }
    }
}
