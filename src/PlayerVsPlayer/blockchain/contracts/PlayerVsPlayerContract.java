package PlayerVsPlayer.blockchain.contracts;

import io.reactivex.Flowable;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import org.web3j.abi.EventEncoder;
import org.web3j.abi.FunctionEncoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.Array;
import org.web3j.abi.datatypes.Bool;
import org.web3j.abi.datatypes.DynamicArray;
import org.web3j.abi.datatypes.Event;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.generated.Bytes32;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameter;
import org.web3j.protocol.core.RemoteCall;
import org.web3j.protocol.core.RemoteFunctionCall;
import org.web3j.protocol.core.methods.request.EthFilter;
import org.web3j.protocol.core.methods.response.BaseEventResponse;
import org.web3j.protocol.core.methods.response.Log;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.tx.Contract;
import org.web3j.tx.TransactionManager;
import org.web3j.tx.gas.ContractGasProvider;

/**
 * <p>
 * Auto generated code.
 * <p>
 * <strong>Do not modify!</strong>
 * <p>
 * Please use the <a href="https://docs.web3j.io/command_line.html">web3j
 * command line tools</a>,
 * or the org.web3j.codegen.SolidityFunctionWrapperGenerator in the
 * <a href="https://github.com/web3j/web3j/tree/master/codegen">codegen
 * module</a> to update.
 *
 * <p>
 * Generated with web3j version 1.5.0.
 */
@SuppressWarnings("rawtypes")
public class PlayerVsPlayerContract extends Contract {
    public static final String BINARY = "6080604052620186a06002556109c460035534801561001d57600080fd5b5060405161188738038061188783398101604081905261003c916100c8565b338061006257604051631e4fbdf760e01b81526000600482015260240160405180910390fd5b61006b81610078565b50600180556004556100e1565b600080546001600160a01b038381166001600160a01b0319831681178455604051919092169283917f8be0079c531659141344cd1fd0a4f28419497f9722a3daafe3b4186f6b6457e09190a35050565b6000602082840312156100da57600080fd5b5051919050565b611797806100f06000396000f3fe6080604052600436106101a05760003560e01c80638a71bb2d116100ec578063dfeab1f01161008a578063e69919ae11610064578063e69919ae14610559578063f071db5a1461056f578063f2fde38b14610585578063f6761151146105a5576101c5565b8063dfeab1f0146104ca578063e30857081461050d578063e31cbd7a14610523576101c5565b8063a34ef304116100c6578063a34ef30414610411578063c835f49414610449578063c9ab861c1461047f578063d4aae80f146104b5576101c5565b80638a71bb2d146103b65780638da5cb5b146103cc5780638e060e21146103fe576101c5565b80635aaa7491116101595780636ab0ad24116101335780636ab0ad241461034c578063715018a61461036c578063857e0ee11461038357806389c22c19146103a3576101c5565b80635aaa7491146102d25780635b6d9c57146102ff5780636a1745501461031f576101c5565b806328b7a812146101e05780632aff55b4146102155780633f8665991461023557806340db2f3014610270578063417e92ca146102905780634e33f83b146102a5576101c5565b366101c5576040516343ac6b4760e11b81523360048201526024015b60405180910390fd5b60405163c286ce5960e01b81523360048201526024016101bc565b3480156101ec57600080fd5b506102006101fb366004611519565b6105c5565b60405190151581526020015b60405180910390f35b34801561022157600080fd5b50610200610230366004611519565b6105e9565b34801561024157600080fd5b50610262610250366004611519565b60056020526000908152604090205481565b60405190815260200161020c565b34801561027c57600080fd5b5061026261028b366004611532565b610613565b34801561029c57600080fd5b50600354610262565b3480156102b157600080fd5b506102626102c036600461156b565b60096020526000908152604090205481565b3480156102de57600080fd5b506102626102ed366004611519565b60009081526005602052604090205490565b34801561030b57600080fd5b5061020061031a36600461156b565b610644565b34801561032b57600080fd5b5061033f61033a366004611519565b610725565b60405161020c9190611586565b34801561035857600080fd5b50610200610367366004611519565b610787565b34801561037857600080fd5b506103816107aa565b005b34801561038f57600080fd5b5061020061039e366004611519565b6107be565b6102006103b13660046115ca565b610953565b3480156103c257600080fd5b5061026260035481565b3480156103d857600080fd5b506000546001600160a01b03165b6040516001600160a01b03909116815260200161020c565b61020061040c366004611532565b61100e565b34801561041d57600080fd5b5061026261042c366004611645565b600860209081526000928352604080842090915290825290205481565b34801561045557600080fd5b5061026261046436600461156b565b6001600160a01b031660009081526009602052604090205490565b34801561048b57600080fd5b506103e661049a366004611519565b6000908152600760205260409020546001600160a01b031690565b3480156104c157600080fd5b50600a54610262565b3480156104d657600080fd5b506102626104e5366004611645565b60009182526008602090815260408084206001600160a01b0393909316845291905290205490565b34801561051957600080fd5b5061026260045481565b34801561052f57600080fd5b506103e661053e366004611519565b6007602052600090815260409020546001600160a01b031681565b34801561056557600080fd5b5061026260025481565b34801561057b57600080fd5b50610262600a5481565b34801561059157600080fd5b506103816105a036600461156b565b6111c2565b3480156105b157600080fd5b506102006105c036600461156b565b611200565b600081815260066020526040812054156105e157506001919050565b506000919050565b60006105f3611335565b60025482111561060557506000919050565b50600381905560015b919050565b6006602052816000526040600020818154811061062f57600080fd5b90600052602060002001600091509150505481565b600061064e611335565b610656611362565b600a543390600081900361066f5760009250505061071c565b6000600a556001600160a01b038416610686578193505b6000846001600160a01b03168260405160006040518083038185875af1925050503d80600081146106d3576040519150601f19603f3d011682016040523d82523d6000602084013e6106d8565b606091505b50509050806107145760405163abe1cff160e01b81526001600160a01b03808516600483015286166024820152604481018390526064016101bc565b600193505050505b61060e60018055565b60008181526006602090815260409182902080548351818402810184019094528084526060939283018282801561077b57602002820191906000526020600020905b815481526020019060010190808311610767575b50505050509050919050565b6000610791611335565b816000036107a157506000919050565b50600455600190565b6107b2611335565b6107bc600061138c565b565b60006107c8611335565b60008281526006602090815260408083208054825181850281018501909352808352919290919083018282801561081e57602002820191906000526020600020905b81548152602001906001019080831161080a575b505050505090506000815190508060000361083d575060009392505050565b60008481526005602090815260408083205460069092528220909161086291906114e7565b60008581526005602052604081208190555b8281101561090d57600084828151811061089057610890611671565b60209081029190910181015160008181526007835260408082208054600886528284206001600160a01b03821680865290875283852080546001600160a01b031990931690935591849055600990955290822080549395509093928392906108f990849061169d565b909155505060019093019250610874915050565b506040805182815242602082015286917fcb601e0243d6768249e96d243d62e4b1da5169175ef058af8c5561e23f016148910160405180910390a2506001949350505050565b600061095d611335565b6109fa604051806101e0016040528060006001600160801b0316815260200160006001600160801b0316815260200160006001600160601b0316815260200160006001600160a01b0316815260200160008152602001600081526020016000815260200160008152602001600081526020016000815260200160008152602001600081526020016060815260200160608152602001606081525090565b6000849003610a0d576000915050611007565b610a16836105c5565b610a24576000915050611007565b60006040808301829052848252600660209081529181902080548251818502810185019093528083529192909190830182828015610a8157602002820191906000526020600020905b815481526020019060010190808311610a6d575b50505050610180830182905250516001600160801b0390811682528416602082015260005b81516001600160801b0316811015610b395760005b82602001516001600160801b0316811015610b3057868682818110610ae257610ae2611671565b905060200201358361018001518381518110610b0057610b00611671565b602002602001015103610b2857826040018051610b1c906116b0565b6001600160601b031690525b600101610abb565b50600101610aa6565b5080604001516001600160601b0316600003610b59576000915050611007565b600083815260056020908152604080832054608085015285835260069091528120610b83916114e7565b60008381526005602090815260408220919091558101516001600160801b031667ffffffffffffffff811115610bbb57610bbb6116d6565b604051908082528060200260200182016040528015610be4578160200160208202803683370190505b506101c082015260208101516001600160801b031667ffffffffffffffff811115610c1157610c116116d6565b604051908082528060200260200182016040528015610c3a578160200160208202803683370190505b506101a0820152600060a082018190525b81602001516001600160801b0316811015610e4b5760076000878784818110610c7657610c76611671565b602090810292909201358352508101919091526040016000908120546001600160a01b03166060840152600890878784818110610cb557610cb5611671565b60209081029290920135835250818101929092526040908101600090812060608601516001600160a01b0316825290925281205460c0840152600790878784818110610d0357610d03611671565b90506020020135815260200190815260200160002060006101000a8154906001600160a01b03021916905560086000878784818110610d4457610d44611671565b905060200201358152602001908152602001600020600083606001516001600160a01b03166001600160a01b03168152602001908152602001600020600090558160c001516009600084606001516001600160a01b03166001600160a01b031681526020019081526020016000206000828254610dc1919061169d565b909155505060c082015160a083018051610ddc90839061169d565b90525060608201516101c0830151805183908110610dfc57610dfc611671565b60200260200101906001600160a01b031690816001600160a01b0316815250508160c00151826101a001518281518110610e3857610e38611671565b6020908102919091010152600101610c4b565b508060a001518160800151610e6091906116ec565b60e0820181905260025461012083018190526003549091610e8191906116ff565b610e8b9190611716565b610100820181905260e082018051610ea49083906116ec565b905250610100810151600a8054600090610ebf90849061169d565b90915550600090505b81602001516001600160801b0316811015610fae578160a00151826101200151836101a00151600081518110610f0057610f00611671565b6020026020010151610f1291906116ff565b610f1c9190611716565b610140830181905261012083015160e08401519091610f3a916116ff565b610f449190611716565b61016083018190526101c0830151805160099160009185908110610f6a57610f6a611671565b60200260200101516001600160a01b03166001600160a01b031681526020019081526020016000206000828254610fa1919061169d565b9091555050600101610ec8565b508484604051610fbf929190611738565b6040518091039020837fe8d7f21feb5ed76862c7af111e1b00bf8784a9729609d1a83088d0b617e6e3d342604051610ff991815260200190565b60405180910390a360019150505b9392505050565b6000600454633b9aca0061102291906116ff565b34101561106b573360045461103b90633b9aca006116ff565b60405163754bed3560e11b81526001600160a01b03909216600483015260248201523460448201526064016101bc565b3334611076856105c5565b61109157611086828286886113dc565b6001925050506111bc565b6000848152600760205260409020546001600160a01b0316156110f75760008481526008602090815260408083206001600160a01b03861680855292529182902054915163289c975560e01b8152600481019190915260248101919091526044016101bc565b600085815260066020908152604080832080546001810182559084528284200187905587835260059091528120805483929061113490849061169d565b9091555050600084815260076020908152604080832080546001600160a01b0319166001600160a01b0387169081179091556008835281842081855283529281902084905580518481524292810192909252869188917ff6efe4596dd741b1531189402c90428b500f305e2be5d49f9e52929c0b185786910160405180910390a46001925050505b92915050565b6111ca611335565b6001600160a01b0381166111f457604051631e4fbdf760e01b8152600060048201526024016101bc565b6111fd8161138c565b50565b600061120a611362565b336000818152600960205260408120549081900361122d5760009250505061071c565b6001600160a01b038083166000908152600960205260408120558416611251578193505b6000846001600160a01b03168260405160006040518083038185875af1925050503d806000811461129e576040519150601f19603f3d011682016040523d82523d6000602084013e6112a3565b606091505b50509050806112df5760405163abe1cff160e01b81526001600160a01b03808516600483015286166024820152604481018390526064016101bc565b604080516001600160a01b038781168252602082018590528516917f90cdf06735ab1f1ad9ff5f6bc879c58d198b4b979f317a852b3a186e2d3c7d59910160405180910390a26001935050505061060e60018055565b6000546001600160a01b031633146107bc5760405163118cdaa760e01b81523360048201526024016101bc565b60026001540361138557604051633ee5aeb560e01b815260040160405180910390fd5b6002600155565b600080546001600160a01b038381166001600160a01b0319831681178455604051919092169283917f8be0079c531659141344cd1fd0a4f28419497f9722a3daafe3b4186f6b6457e09190a35050565b600081815260066020908152604080832080546001810182559084528284200185905583835260059091528120805485929061141990849061169d565b9091555050600082815260076020908152604080832080546001600160a01b0319166001600160a01b0389169081179091556008835281842081855283529281902086905580518681524292810192909252849184917f7292d0191fea8f8abf94a6b61445934c087c11a732cab3bf7660af01a05a4073910160405180910390a4604080518481524260208201526001600160a01b03861691849184917ff6efe4596dd741b1531189402c90428b500f305e2be5d49f9e52929c0b185786910160405180910390a450505050565b50805460008255906000526020600020908101906111fd91905b808211156115155760008155600101611501565b5090565b60006020828403121561152b57600080fd5b5035919050565b6000806040838503121561154557600080fd5b50508035926020909101359150565b80356001600160a01b038116811461060e57600080fd5b60006020828403121561157d57600080fd5b61100782611554565b6020808252825182820181905260009190848201906040850190845b818110156115be578351835292840192918401916001016115a2565b50909695505050505050565b6000806000604084860312156115df57600080fd5b833567ffffffffffffffff808211156115f757600080fd5b818601915086601f83011261160b57600080fd5b81358181111561161a57600080fd5b8760208260051b850101111561162f57600080fd5b6020928301989097509590910135949350505050565b6000806040838503121561165857600080fd5b8235915061166860208401611554565b90509250929050565b634e487b7160e01b600052603260045260246000fd5b634e487b7160e01b600052601160045260246000fd5b808201808211156111bc576111bc611687565b60006001600160601b038083168181036116cc576116cc611687565b6001019392505050565b634e487b7160e01b600052604160045260246000fd5b818103818111156111bc576111bc611687565b80820281158282048414176111bc576111bc611687565b60008261173357634e487b7160e01b600052601260045260246000fd5b500490565b60006001600160fb1b0383111561174e57600080fd5b8260051b8085843791909101939250505056fea26469706673582212203c9b00f328d3ed43ea4ed6e63db7e0391c0de8792fb1bffa97e3bdb2091a3ec664736f6c63430008170033\n";

    public static final String FUNC_BETGAME = "BetGame";

    public static final String FUNC_ENDGAME = "EndGame";

    public static final String FUNC_GAMEFAIL = "GameFail";

    public static final String FUNC_SETMINIMUMBET = "SetMinimumBet";

    public static final String FUNC_SETPROTOCOLROYALTY = "SetProtocolRoyalty";

    public static final String FUNC_WITHDRAW = "Withdraw";

    public static final String FUNC_WITHDRAWFEESOWNER = "WithdrawFeesOwner";

    public static final String FUNC_FEESCOLLECTED = "feesCollected";

    public static final String FUNC_GAMEBALANCE = "gameBalance";

    public static final String FUNC_GAMEPLAYERSIDS = "gamePlayersIds";

    public static final String FUNC_GETACTIVEGAME = "getActiveGame";

    public static final String FUNC_GETFEESCOLLECTED = "getFeesCollected";

    public static final String FUNC_GETPLAYERADDRESS = "getPlayerAddress";

    public static final String FUNC_GETPLAYERCONTRACTBALANCE = "getPlayerContractBalance";

    public static final String FUNC_GETPLAYERGAMEBALANCE = "getPlayerGameBalance";

    public static final String FUNC_GETPLAYERSIDS = "getPlayersIds";

    public static final String FUNC_GETPROTOCOLGAMEBALANCE = "getProtocolGameBalance";

    public static final String FUNC_GETPROTOCOLROYALTY = "getProtocolRoyalty";

    public static final String FUNC_MINIMUMBETGWEI = "minimumBetGwei";

    public static final String FUNC_OWNER = "owner";

    public static final String FUNC_PC = "pc";

    public static final String FUNC_PLAYERADDRESS = "playerAddress";

    public static final String FUNC_PLAYERBALANCE = "playerBalance";

    public static final String FUNC_PLAYERGAMEBALANCE = "playerGameBalance";

    public static final String FUNC_RENOUNCEOWNERSHIP = "renounceOwnership";

    public static final String FUNC_ROYALTYPERCENTAGE = "royaltyPercentage";

    public static final String FUNC_TRANSFEROWNERSHIP = "transferOwnership";

    public static final Event GAMEENDED_EVENT = new Event("GameEnded",
            Arrays.<TypeReference<?>>asList(new TypeReference<Bytes32>(true) {
            }, new TypeReference<DynamicArray<Bytes32>>(true) {
            }, new TypeReference<Uint256>() {
            }));;

    public static final Event GAMEFAILED_EVENT = new Event("GameFailed",
            Arrays.<TypeReference<?>>asList(new TypeReference<Bytes32>(true) {
            }, new TypeReference<Uint256>() {
            }, new TypeReference<Uint256>() {
            }));;

    public static final Event GAMESTARTED_EVENT = new Event("GameStarted",
            Arrays.<TypeReference<?>>asList(new TypeReference<Bytes32>(true) {
            }, new TypeReference<Bytes32>(true) {
            }, new TypeReference<Address>(true) {
            }, new TypeReference<Uint256>() {
            }, new TypeReference<Uint256>() {
            }));;

    public static final Event OWNERSHIPTRANSFERRED_EVENT = new Event("OwnershipTransferred",
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>(true) {
            }, new TypeReference<Address>(true) {
            }));;

    public static final Event PLAYERBET_EVENT = new Event("PlayerBet",
            Arrays.<TypeReference<?>>asList(new TypeReference<Bytes32>(true) {
            }, new TypeReference<Bytes32>(true) {
            }, new TypeReference<Address>(true) {
            }, new TypeReference<Uint256>() {
            }, new TypeReference<Uint256>() {
            }));;

    public static final Event PLAYERWITHDRAW_EVENT = new Event("PlayerWithdraw",
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>(true) {
            }, new TypeReference<Address>() {
            }, new TypeReference<Uint256>() {
            }));;

    @Deprecated
    protected PlayerVsPlayerContract(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice,
            BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    protected PlayerVsPlayerContract(String contractAddress, Web3j web3j, Credentials credentials,
            ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, credentials, contractGasProvider);
    }

    @Deprecated
    protected PlayerVsPlayerContract(String contractAddress, Web3j web3j, TransactionManager transactionManager,
            BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    protected PlayerVsPlayerContract(String contractAddress, Web3j web3j, TransactionManager transactionManager,
            ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public RemoteFunctionCall<TransactionReceipt> BetGame(byte[] gameId, byte[] playerId, BigInteger weiValue) {
        final Function function = new Function(
                FUNC_BETGAME,
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Bytes32(gameId),
                        new org.web3j.abi.datatypes.generated.Bytes32(playerId)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function, weiValue);
    }

    public RemoteFunctionCall<TransactionReceipt> EndGame(List<byte[]> winnersIds, byte[] gameId, BigInteger weiValue) {
        final Function function = new Function(
                FUNC_ENDGAME,
                Arrays.<Type>asList(new org.web3j.abi.datatypes.DynamicArray<org.web3j.abi.datatypes.generated.Bytes32>(
                        org.web3j.abi.datatypes.generated.Bytes32.class,
                        org.web3j.abi.Utils.typeMap(winnersIds, org.web3j.abi.datatypes.generated.Bytes32.class)),
                        new org.web3j.abi.datatypes.generated.Bytes32(gameId)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function, weiValue);
    }

    public RemoteFunctionCall<TransactionReceipt> GameFail(byte[] gameId) {
        final Function function = new Function(
                FUNC_GAMEFAIL,
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Bytes32(gameId)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> SetMinimumBet(BigInteger _minimumBetGwei) {
        final Function function = new Function(
                FUNC_SETMINIMUMBET,
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_minimumBetGwei)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> SetProtocolRoyalty(BigInteger percentage) {
        final Function function = new Function(
                FUNC_SETPROTOCOLROYALTY,
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(percentage)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> Withdraw(String destinationAddr) {
        final Function function = new Function(
                FUNC_WITHDRAW,
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, destinationAddr)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> WithdrawFeesOwner(String destinationAddr) {
        final Function function = new Function(
                FUNC_WITHDRAWFEESOWNER,
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, destinationAddr)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<BigInteger> feesCollected() {
        final Function function = new Function(FUNC_FEESCOLLECTED,
                Arrays.<Type>asList(),
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {
                }));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteFunctionCall<BigInteger> gameBalance(byte[] param0) {
        final Function function = new Function(FUNC_GAMEBALANCE,
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Bytes32(param0)),
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {
                }));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteFunctionCall<byte[]> gamePlayersIds(byte[] param0, BigInteger param1) {
        final Function function = new Function(FUNC_GAMEPLAYERSIDS,
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Bytes32(param0),
                        new org.web3j.abi.datatypes.generated.Uint256(param1)),
                Arrays.<TypeReference<?>>asList(new TypeReference<Bytes32>() {
                }));
        return executeRemoteCallSingleValueReturn(function, byte[].class);
    }

    public RemoteFunctionCall<Boolean> getActiveGame(byte[] gameId) {
        final Function function = new Function(FUNC_GETACTIVEGAME,
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Bytes32(gameId)),
                Arrays.<TypeReference<?>>asList(new TypeReference<Bool>() {
                }));
        return executeRemoteCallSingleValueReturn(function, Boolean.class);
    }

    public RemoteFunctionCall<BigInteger> getFeesCollected() {
        final Function function = new Function(FUNC_GETFEESCOLLECTED,
                Arrays.<Type>asList(),
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {
                }));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteFunctionCall<String> getPlayerAddress(byte[] playerid) {
        final Function function = new Function(FUNC_GETPLAYERADDRESS,
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Bytes32(playerid)),
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {
                }));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteFunctionCall<BigInteger> getPlayerContractBalance(String player) {
        final Function function = new Function(FUNC_GETPLAYERCONTRACTBALANCE,
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, player)),
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {
                }));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteFunctionCall<BigInteger> getPlayerGameBalance(byte[] playerId, String player) {
        final Function function = new Function(FUNC_GETPLAYERGAMEBALANCE,
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Bytes32(playerId),
                        new org.web3j.abi.datatypes.Address(160, player)),
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {
                }));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteFunctionCall<List> getPlayersIds(byte[] gameId) {
        final Function function = new Function(FUNC_GETPLAYERSIDS,
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Bytes32(gameId)),
                Arrays.<TypeReference<?>>asList(new TypeReference<DynamicArray<Bytes32>>() {
                }));
        return new RemoteFunctionCall<List>(function,
                new Callable<List>() {
                    @Override
                    @SuppressWarnings("unchecked")
                    public List call() throws Exception {
                        List<Type> result = (List<Type>) executeCallSingleValueReturn(function, List.class);
                        return convertToNative(result);
                    }
                });
    }

    public RemoteFunctionCall<BigInteger> getProtocolGameBalance(byte[] gameId) {
        final Function function = new Function(FUNC_GETPROTOCOLGAMEBALANCE,
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Bytes32(gameId)),
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {
                }));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteFunctionCall<BigInteger> getProtocolRoyalty() {
        final Function function = new Function(FUNC_GETPROTOCOLROYALTY,
                Arrays.<Type>asList(),
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {
                }));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteFunctionCall<BigInteger> minimumBetGwei() {
        final Function function = new Function(FUNC_MINIMUMBETGWEI,
                Arrays.<Type>asList(),
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {
                }));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteFunctionCall<String> owner() {
        final Function function = new Function(FUNC_OWNER,
                Arrays.<Type>asList(),
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {
                }));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteFunctionCall<BigInteger> pc() {
        final Function function = new Function(FUNC_PC,
                Arrays.<Type>asList(),
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {
                }));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteFunctionCall<String> playerAddress(byte[] param0) {
        final Function function = new Function(FUNC_PLAYERADDRESS,
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Bytes32(param0)),
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {
                }));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteFunctionCall<BigInteger> playerBalance(String param0) {
        final Function function = new Function(FUNC_PLAYERBALANCE,
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, param0)),
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {
                }));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteFunctionCall<BigInteger> playerGameBalance(byte[] param0, String param1) {
        final Function function = new Function(FUNC_PLAYERGAMEBALANCE,
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Bytes32(param0),
                        new org.web3j.abi.datatypes.Address(160, param1)),
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {
                }));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteFunctionCall<TransactionReceipt> renounceOwnership() {
        final Function function = new Function(
                FUNC_RENOUNCEOWNERSHIP,
                Arrays.<Type>asList(),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<BigInteger> royaltyPercentage() {
        final Function function = new Function(FUNC_ROYALTYPERCENTAGE,
                Arrays.<Type>asList(),
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {
                }));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteFunctionCall<TransactionReceipt> transferOwnership(String newOwner) {
        final Function function = new Function(
                FUNC_TRANSFEROWNERSHIP,
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, newOwner)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public static List<GameEndedEventResponse> getGameEndedEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = staticExtractEventParametersWithLog(GAMEENDED_EVENT,
                transactionReceipt);
        ArrayList<GameEndedEventResponse> responses = new ArrayList<GameEndedEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            GameEndedEventResponse typedResponse = new GameEndedEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.gameId = (byte[]) eventValues.getIndexedValues().get(0).getValue();
            typedResponse.winners = (byte[]) ((Array) eventValues.getIndexedValues().get(1)).getNativeValueCopy()
                    .toString().getBytes();

            typedResponse.time = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public static GameEndedEventResponse getGameEndedEventFromLog(Log log) {
        Contract.EventValuesWithLog eventValues = staticExtractEventParametersWithLog(GAMEENDED_EVENT, log);
        GameEndedEventResponse typedResponse = new GameEndedEventResponse();
        typedResponse.log = log;
        typedResponse.gameId = (byte[]) eventValues.getIndexedValues().get(0).getValue();
        typedResponse.winners = (byte[]) ((Array) eventValues.getIndexedValues().get(1)).getNativeValueCopy().toString()
                .getBytes();
        typedResponse.time = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
        return typedResponse;
    }
    /*
     * I solved it with `.toString().getBytes()`:
     * 
     * ```
     * typedResponse.winners = (byte[]) ((Array)
     * eventValues.getIndexedValues().get(1)).getNativeValueCopy().toString().
     * getBytes();
     * ```
     * 
     * and
     * 
     * ```
     * typedResponse.winners = (byte[]) ((Array)
     * eventValues.getIndexedValues().get(1)).getNativeValueCopy().toString().
     * getBytes();
     * ```
     * 
     * Not sure it will work correctly,
     */

    public Flowable<GameEndedEventResponse> gameEndedEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(log -> getGameEndedEventFromLog(log));
    }

    public Flowable<GameEndedEventResponse> gameEndedEventFlowable(DefaultBlockParameter startBlock,
            DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(GAMEENDED_EVENT));
        return gameEndedEventFlowable(filter);
    }

    public static List<GameFailedEventResponse> getGameFailedEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = staticExtractEventParametersWithLog(GAMEFAILED_EVENT,
                transactionReceipt);
        ArrayList<GameFailedEventResponse> responses = new ArrayList<GameFailedEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            GameFailedEventResponse typedResponse = new GameFailedEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.gameId = (byte[]) eventValues.getIndexedValues().get(0).getValue();
            typedResponse.gameBalance = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
            typedResponse.time = (BigInteger) eventValues.getNonIndexedValues().get(1).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public static GameFailedEventResponse getGameFailedEventFromLog(Log log) {
        Contract.EventValuesWithLog eventValues = staticExtractEventParametersWithLog(GAMEFAILED_EVENT, log);
        GameFailedEventResponse typedResponse = new GameFailedEventResponse();
        typedResponse.log = log;
        typedResponse.gameId = (byte[]) eventValues.getIndexedValues().get(0).getValue();
        typedResponse.gameBalance = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
        typedResponse.time = (BigInteger) eventValues.getNonIndexedValues().get(1).getValue();
        return typedResponse;
    }

    public Flowable<GameFailedEventResponse> gameFailedEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(log -> getGameFailedEventFromLog(log));
    }

    public Flowable<GameFailedEventResponse> gameFailedEventFlowable(DefaultBlockParameter startBlock,
            DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(GAMEFAILED_EVENT));
        return gameFailedEventFlowable(filter);
    }

    public static List<GameStartedEventResponse> getGameStartedEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = staticExtractEventParametersWithLog(GAMESTARTED_EVENT,
                transactionReceipt);
        ArrayList<GameStartedEventResponse> responses = new ArrayList<GameStartedEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            GameStartedEventResponse typedResponse = new GameStartedEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.gameId = (byte[]) eventValues.getIndexedValues().get(0).getValue();
            typedResponse.playerId = (byte[]) eventValues.getIndexedValues().get(1).getValue();
            typedResponse.player = (String) eventValues.getIndexedValues().get(2).getValue();
            typedResponse.amount = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
            typedResponse.time = (BigInteger) eventValues.getNonIndexedValues().get(1).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public static GameStartedEventResponse getGameStartedEventFromLog(Log log) {
        Contract.EventValuesWithLog eventValues = staticExtractEventParametersWithLog(GAMESTARTED_EVENT, log);
        GameStartedEventResponse typedResponse = new GameStartedEventResponse();
        typedResponse.log = log;
        typedResponse.gameId = (byte[]) eventValues.getIndexedValues().get(0).getValue();
        typedResponse.playerId = (byte[]) eventValues.getIndexedValues().get(1).getValue();
        typedResponse.player = (String) eventValues.getIndexedValues().get(2).getValue();
        typedResponse.amount = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
        typedResponse.time = (BigInteger) eventValues.getNonIndexedValues().get(1).getValue();
        return typedResponse;
    }

    public Flowable<GameStartedEventResponse> gameStartedEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(log -> getGameStartedEventFromLog(log));
    }

    public Flowable<GameStartedEventResponse> gameStartedEventFlowable(DefaultBlockParameter startBlock,
            DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(GAMESTARTED_EVENT));
        return gameStartedEventFlowable(filter);
    }

    public static List<OwnershipTransferredEventResponse> getOwnershipTransferredEvents(
            TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = staticExtractEventParametersWithLog(OWNERSHIPTRANSFERRED_EVENT,
                transactionReceipt);
        ArrayList<OwnershipTransferredEventResponse> responses = new ArrayList<OwnershipTransferredEventResponse>(
                valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            OwnershipTransferredEventResponse typedResponse = new OwnershipTransferredEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.previousOwner = (String) eventValues.getIndexedValues().get(0).getValue();
            typedResponse.newOwner = (String) eventValues.getIndexedValues().get(1).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public static OwnershipTransferredEventResponse getOwnershipTransferredEventFromLog(Log log) {
        Contract.EventValuesWithLog eventValues = staticExtractEventParametersWithLog(OWNERSHIPTRANSFERRED_EVENT, log);
        OwnershipTransferredEventResponse typedResponse = new OwnershipTransferredEventResponse();
        typedResponse.log = log;
        typedResponse.previousOwner = (String) eventValues.getIndexedValues().get(0).getValue();
        typedResponse.newOwner = (String) eventValues.getIndexedValues().get(1).getValue();
        return typedResponse;
    }

    public Flowable<OwnershipTransferredEventResponse> ownershipTransferredEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(log -> getOwnershipTransferredEventFromLog(log));
    }

    public Flowable<OwnershipTransferredEventResponse> ownershipTransferredEventFlowable(
            DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(OWNERSHIPTRANSFERRED_EVENT));
        return ownershipTransferredEventFlowable(filter);
    }

    public static List<PlayerBetEventResponse> getPlayerBetEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = staticExtractEventParametersWithLog(PLAYERBET_EVENT,
                transactionReceipt);
        ArrayList<PlayerBetEventResponse> responses = new ArrayList<PlayerBetEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            PlayerBetEventResponse typedResponse = new PlayerBetEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.gameId = (byte[]) eventValues.getIndexedValues().get(0).getValue();
            typedResponse.playerId = (byte[]) eventValues.getIndexedValues().get(1).getValue();
            typedResponse.player = (String) eventValues.getIndexedValues().get(2).getValue();
            typedResponse.amount = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
            typedResponse.time = (BigInteger) eventValues.getNonIndexedValues().get(1).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public static PlayerBetEventResponse getPlayerBetEventFromLog(Log log) {
        Contract.EventValuesWithLog eventValues = staticExtractEventParametersWithLog(PLAYERBET_EVENT, log);
        PlayerBetEventResponse typedResponse = new PlayerBetEventResponse();
        typedResponse.log = log;
        typedResponse.gameId = (byte[]) eventValues.getIndexedValues().get(0).getValue();
        typedResponse.playerId = (byte[]) eventValues.getIndexedValues().get(1).getValue();
        typedResponse.player = (String) eventValues.getIndexedValues().get(2).getValue();
        typedResponse.amount = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
        typedResponse.time = (BigInteger) eventValues.getNonIndexedValues().get(1).getValue();
        return typedResponse;
    }

    public Flowable<PlayerBetEventResponse> playerBetEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(log -> getPlayerBetEventFromLog(log));
    }

    public Flowable<PlayerBetEventResponse> playerBetEventFlowable(DefaultBlockParameter startBlock,
            DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(PLAYERBET_EVENT));
        return playerBetEventFlowable(filter);
    }

    public static List<PlayerWithdrawEventResponse> getPlayerWithdrawEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = staticExtractEventParametersWithLog(PLAYERWITHDRAW_EVENT,
                transactionReceipt);
        ArrayList<PlayerWithdrawEventResponse> responses = new ArrayList<PlayerWithdrawEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            PlayerWithdrawEventResponse typedResponse = new PlayerWithdrawEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.player = (String) eventValues.getIndexedValues().get(0).getValue();
            typedResponse.destinationAddress = (String) eventValues.getNonIndexedValues().get(0).getValue();
            typedResponse.amount = (BigInteger) eventValues.getNonIndexedValues().get(1).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public static PlayerWithdrawEventResponse getPlayerWithdrawEventFromLog(Log log) {
        Contract.EventValuesWithLog eventValues = staticExtractEventParametersWithLog(PLAYERWITHDRAW_EVENT, log);
        PlayerWithdrawEventResponse typedResponse = new PlayerWithdrawEventResponse();
        typedResponse.log = log;
        typedResponse.player = (String) eventValues.getIndexedValues().get(0).getValue();
        typedResponse.destinationAddress = (String) eventValues.getNonIndexedValues().get(0).getValue();
        typedResponse.amount = (BigInteger) eventValues.getNonIndexedValues().get(1).getValue();
        return typedResponse;
    }

    public Flowable<PlayerWithdrawEventResponse> playerWithdrawEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(log -> getPlayerWithdrawEventFromLog(log));
    }

    public Flowable<PlayerWithdrawEventResponse> playerWithdrawEventFlowable(DefaultBlockParameter startBlock,
            DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(PLAYERWITHDRAW_EVENT));
        return playerWithdrawEventFlowable(filter);
    }

    @Deprecated
    public static PlayerVsPlayerContract load(String contractAddress, Web3j web3j, Credentials credentials,
            BigInteger gasPrice,
            BigInteger gasLimit) {
        return new PlayerVsPlayerContract(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    @Deprecated
    public static PlayerVsPlayerContract load(String contractAddress, Web3j web3j,
            TransactionManager transactionManager,
            BigInteger gasPrice, BigInteger gasLimit) {
        return new PlayerVsPlayerContract(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public static PlayerVsPlayerContract load(String contractAddress, Web3j web3j, Credentials credentials,
            ContractGasProvider contractGasProvider) {
        return new PlayerVsPlayerContract(contractAddress, web3j, credentials, contractGasProvider);
    }

    public static PlayerVsPlayerContract load(String contractAddress, Web3j web3j,
            TransactionManager transactionManager,
            ContractGasProvider contractGasProvider) {
        return new PlayerVsPlayerContract(contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public static RemoteCall<PlayerVsPlayerContract> deploy(Web3j web3j, Credentials credentials,
            ContractGasProvider contractGasProvider, BigInteger _minimumBetGwei) {
        String encodedConstructor = FunctionEncoder
                .encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_minimumBetGwei)));
        return deployRemoteCall(PlayerVsPlayerContract.class, web3j, credentials, contractGasProvider, BINARY,
                encodedConstructor);
    }

    public static RemoteCall<PlayerVsPlayerContract> deploy(Web3j web3j, TransactionManager transactionManager,
            ContractGasProvider contractGasProvider, BigInteger _minimumBetGwei) {
        String encodedConstructor = FunctionEncoder
                .encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_minimumBetGwei)));
        return deployRemoteCall(PlayerVsPlayerContract.class, web3j, transactionManager, contractGasProvider, BINARY,
                encodedConstructor);
    }

    @Deprecated
    public static RemoteCall<PlayerVsPlayerContract> deploy(Web3j web3j, Credentials credentials, BigInteger gasPrice,
            BigInteger gasLimit, BigInteger _minimumBetGwei) {
        String encodedConstructor = FunctionEncoder
                .encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_minimumBetGwei)));
        return deployRemoteCall(PlayerVsPlayerContract.class, web3j, credentials, gasPrice, gasLimit, BINARY,
                encodedConstructor);
    }

    @Deprecated
    public static RemoteCall<PlayerVsPlayerContract> deploy(Web3j web3j, TransactionManager transactionManager,
            BigInteger gasPrice, BigInteger gasLimit, BigInteger _minimumBetGwei) {
        String encodedConstructor = FunctionEncoder
                .encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_minimumBetGwei)));
        return deployRemoteCall(PlayerVsPlayerContract.class, web3j, transactionManager, gasPrice, gasLimit, BINARY,
                encodedConstructor);
    }

    public static class GameEndedEventResponse extends BaseEventResponse {
        public byte[] gameId;

        public byte[] winners;

        public BigInteger time;
    }

    public static class GameFailedEventResponse extends BaseEventResponse {
        public byte[] gameId;

        public BigInteger gameBalance;

        public BigInteger time;
    }

    public static class GameStartedEventResponse extends BaseEventResponse {
        public byte[] gameId;

        public byte[] playerId;

        public String player;

        public BigInteger amount;

        public BigInteger time;
    }

    public static class OwnershipTransferredEventResponse extends BaseEventResponse {
        public String previousOwner;

        public String newOwner;
    }

    public static class PlayerBetEventResponse extends BaseEventResponse {
        public byte[] gameId;

        public byte[] playerId;

        public String player;

        public BigInteger amount;

        public BigInteger time;
    }

    public static class PlayerWithdrawEventResponse extends BaseEventResponse {
        public String player;

        public String destinationAddress;

        public BigInteger amount;
    }
}
