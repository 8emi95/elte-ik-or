import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class LottoImpl extends UnicastRemoteObject implements LottoInterface{
    private boolean winner;
    
    public LottoImpl() throws RemoteException {
        super();
    }
    
    public void setWinner(boolean winner) {
        this.winner = winner;
    }
    
    @Override
    public boolean nyeroszamE() throws RemoteException {
        return winner;
    }
}
