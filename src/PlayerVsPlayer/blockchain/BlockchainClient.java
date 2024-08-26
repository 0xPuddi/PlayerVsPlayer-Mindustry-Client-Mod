package PlayerVsPlayer.blockchain;

import PlayerVsPlayer.utils.GeneratorQR;
import PlayerVsPlayer.blockchain.contracts.PlayerVsPlayerContract;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.text.DecimalFormat;
import java.math.RoundingMode;
import java.math.BigInteger;

import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.http.*;
import org.web3j.tx.TransactionManager;
import org.web3j.tx.gas.DefaultGasProvider;
import org.web3j.crypto.WalletUtils;
import org.web3j.crypto.Hash;

import com.google.zxing.WriterException;

import arc.graphics.Pixmap;
import arc.util.Log;

public class BlockchainClient {
    Web3j web3;
    private final String nodeURL;
    private final String contractAddress;
    private final String chainId;
    private final GeneratorQR generatorQR;
    private final NumberFormat formatter;
    private final PlayerVsPlayerContract pvpContract;

    public BlockchainClient(String nodeURL, String contractAddress, String _chainId) {
        this.nodeURL = nodeURL;
        this.web3 = Web3j.build(new HttpService(this.nodeURL));

        this.chainId = _chainId;
        this.contractAddress = contractAddress;
        this.generatorQR = new GeneratorQR();

        this.formatter = new DecimalFormat("0.0E0");
        this.formatter.setRoundingMode(RoundingMode.DOWN);
        this.formatter.setMaximumFractionDigits(5);

        TransactionManager tm = null;
        pvpContract = PlayerVsPlayerContract.load(contractAddress, this.web3, tm, new DefaultGasProvider());
    }

    /*
     * getContractAddress returns the current contractAddress variable
     */
    public String getContractAddress() {
        return this.contractAddress;
    }

    /*
     * isValidAddress returns true if the address is valid and false if it is not
     */
    public boolean isValidAddress(String address) {
        return WalletUtils.isValidAddress(address);
    }

    /*
     * getAddressContractBalance fetch and returns synchronously the contract
     * balance of a user
     */
    public Double getAddressContractBalance(String address) {
        BigInteger balance = BigInteger.ZERO;

        try {
            balance = pvpContract.getPlayerContractBalance(address).send();
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (balance.compareTo(BigInteger.ZERO) <= 0) {
            return 0.0;
        }

        return Double.parseDouble(balance.divide(BigInteger.TEN.pow(18)).toString());
    }

    /*
     * getAddressNativeBalance fetch and returns synchronously the address native
     * balance
     */
    public Double getAddressNativeBalance(String address) {
        BigInteger balance = BigInteger.ZERO;

        try {
            balance = web3.ethGetBalance(address, DefaultBlockParameterName.LATEST).send().getBalance();
        } catch (IOException e) {
            e.printStackTrace();
            return Double.NaN;
        }

        if (balance.compareTo(BigInteger.ZERO) <= 0) {
            Log.info("Litterally 0 balance");
            return 0.0;
        }

        String doubleGweiBalance = balance.divide(BigInteger.TEN.pow(9)).toString();

        if (doubleGweiBalance.length() <= 9) {
            while (true) {
                if (doubleGweiBalance.length() == 9) {
                    doubleGweiBalance = "0.".concat(doubleGweiBalance);
                    break;
                }

                doubleGweiBalance = "0".concat(doubleGweiBalance);
            }
        } else {
            Integer etherIndexInGwei = doubleGweiBalance.length() - 9 - 1;
            doubleGweiBalance = doubleGweiBalance.subSequence(0, etherIndexInGwei - 1) + "."
                    + doubleGweiBalance.substring(etherIndexInGwei);
        }

        return Double.parseDouble(doubleGweiBalance);
    }

    /*
     * createWithdrawTransaction creates and return the transaction's QR code
     */
    public Pixmap createWithdrawTransaction() {
        String transactionStringToEncode = "ethereum:" + this.contractAddress + "@" + this.chainId + "/"
                + PlayerVsPlayerContract.FUNC_WITHDRAW + "?address=0x0000000000000000000000000000000000000000";

        Pixmap pixmapQR = new Pixmap(0, 0);

        try {
            pixmapQR = this.generatorQR.createQRTexture(transactionStringToEncode);
        } catch (WriterException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return pixmapQR;
    }

    /*
     * createBetTransaction creates a betting transaction
     */
    public Pixmap createBetTransaction(Double betAmount, String playerId, String gameId) {
        Pixmap pixmapQR = new Pixmap(0, 0);

        BigDecimal bigDecimalBetAmount = BigDecimal.valueOf(betAmount);
        String betAmountString = formatter.format(bigDecimalBetAmount.multiply(BigDecimal.TEN.pow(18)));

        // fractional value
        if (betAmountString.indexOf("-") != -1) {
            return pixmapQR;
        }

        // number with remaining fraction
        if ((betAmountString.indexOf(".") + betAmountString.indexOf("E")) > Integer
                .valueOf(betAmountString.substring(betAmountString.indexOf("E") + 1))) {
            return pixmapQR;
        }

        String bGameId = Hash.sha3String(gameId);

        String transactionStringToEncode = "ethereum:" + this.contractAddress + "@" + this.chainId + "/"
                + PlayerVsPlayerContract.FUNC_BETGAME + "(bytes32,bytes32)" + "?bytes32=" + bGameId + "&bytes32="
                + playerId + "&value=" + betAmountString;

        try {
            pixmapQR = this.generatorQR.createQRTexture(transactionStringToEncode);
        } catch (WriterException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return pixmapQR;
    }
}
