package com.wjl.citademo.codegen;

import com.citahub.cita.abi.EventEncoder;
import com.citahub.cita.abi.EventValues;
import com.citahub.cita.abi.TypeReference;
import com.citahub.cita.abi.datatypes.Address;
import com.citahub.cita.abi.datatypes.DynamicArray;
import com.citahub.cita.abi.datatypes.Event;
import com.citahub.cita.abi.datatypes.Type;
import com.citahub.cita.abi.datatypes.Utf8String;
import com.citahub.cita.abi.datatypes.generated.Uint256;
import com.citahub.cita.protocol.CITAj;
import com.citahub.cita.protocol.core.DefaultBlockParameter;
import com.citahub.cita.protocol.core.RemoteCall;
import com.citahub.cita.protocol.core.methods.request.AppFilter;
import com.citahub.cita.protocol.core.methods.response.Log;
import com.citahub.cita.protocol.core.methods.response.TransactionReceipt;
import com.citahub.cita.tx.Contract;
import com.citahub.cita.tx.TransactionManager;
import io.reactivex.Flowable;
import io.reactivex.functions.Function;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * <p>Auto generated code.
 * <p><strong>Do not modify!</strong>
 * <p>Please use the <a href="https://github.com/citahub/citaj/tree/master/codegen">codegen module</a> to update.
 *
 * <p>Generated with citaj version 20.2.0.
 */
public class Abi extends Contract {
    public static final String BINARY = "608060405234801561001057600080fd5b506105ef806100206000396000f300608060405260043610610057576000357c0100000000000000000000000000000000000000000000000000000000900463ffffffff16806336555b851461005c578063942b765a146100cf5780639507d39a1461013b575b600080fd5b34801561006857600080fd5b506100cd600480360381019080803590602001908201803590602001908080601f0160208091040260200160405190810160405280939291908181526020018383808284378201915050505050509192919290803590602001909291905050506101e1565b005b3480156100db57600080fd5b506100e461032b565b6040518080602001828103825283818151815260200191508051906020019060200280838360005b8381101561012757808201518184015260208101905061010c565b505050509050019250505060405180910390f35b34801561014757600080fd5b50610166600480360381019080803590602001909291905050506103c0565b6040518080602001828103825283818151815260200191508051906020019080838360005b838110156101a657808201518184015260208101905061018b565b50505050905090810190601f1680156101d35780820380516001836020036101000a031916815260200191505b509250505060405180910390f35b816000803373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1681526020019081526020016000206000838152602001908152602001600020908051906020019061024492919061051e565b5061024f33826104b1565b7fe4af93ca7e370881e6f1b57272e42a3d851d3cc6d951b4f4d2e7a963914468a2338383604051808473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200180602001838152602001828103825284818151815260200191508051906020019080838360005b838110156102eb5780820151818401526020810190506102d0565b50505050905090810190601f1680156103185780820380516001836020036101000a031916815260200191505b5094505050505060405180910390a15050565b6060600160003373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1681526020019081526020016000208054806020026020016040519081016040528092919081815260200182805480156103b657602002820191906000526020600020905b8154815260200190600101908083116103a2575b5050505050905090565b60606000803373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200190815260200160002060008381526020019081526020016000208054600181600116156101000203166002900480601f0160208091040260200160405190810160405280929190818152602001828054600181600116156101000203166002900480156104a55780601f1061047a576101008083540402835291602001916104a5565b820191906000526020600020905b81548152906001019060200180831161048857829003601f168201915b50505050509050919050565b600160008373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1681526020019081526020016000208190806001815401808255809150509060018203906000526020600020016000909192909190915055505050565b828054600181600116156101000203166002900490600052602060002090601f016020900481019282601f1061055f57805160ff191683800117855561058d565b8280016001018555821561058d579182015b8281111561058c578251825591602001919060010190610571565b5b50905061059a919061059e565b5090565b6105c091905b808211156105bc5760008160009055506001016105a4565b5090565b905600a165627a7a723058205e7ac0a4627300f960b4e5556b840200eded32120e0292e0b7c8262fe9a432580029";

    public static final String ABI = "[{\"constant\":false,\"inputs\":[{\"name\":\"text\",\"type\":\"string\",\"indexed\":false},{\"name\":\"time\",\"type\":\"uint256\",\"indexed\":false}],\"name\":\"add\",\"outputs\":[],\"type\":\"function\",\"payable\":false,\"stateMutability\":\"nonpayable\"},{\"constant\":true,\"inputs\":[],\"name\":\"getList\",\"outputs\":[{\"name\":\"\",\"type\":\"uint256[]\",\"indexed\":false}],\"type\":\"function\",\"payable\":false,\"stateMutability\":\"view\"},{\"constant\":true,\"inputs\":[{\"name\":\"time\",\"type\":\"uint256\",\"indexed\":false}],\"name\":\"get\",\"outputs\":[{\"name\":\"\",\"type\":\"string\",\"indexed\":false}],\"type\":\"function\",\"payable\":false,\"stateMutability\":\"view\"},{\"constant\":false,\"inputs\":[{\"name\":\"_sender\",\"type\":\"address\",\"indexed\":false},{\"name\":\"_text\",\"type\":\"string\",\"indexed\":false},{\"name\":\"_time\",\"type\":\"uint256\",\"indexed\":false}],\"name\":\"Recorded\",\"outputs\":null,\"type\":\"event\",\"payable\":false,\"stateMutability\":null}]";

    protected Abi(String contractAddress, CITAj citaj, TransactionManager transactionManager) {
        super(BINARY, contractAddress, citaj, transactionManager);
    }

    public List<RecordedEventResponse> getRecordedEvents(TransactionReceipt transactionReceipt) {
        final Event event = new Event("Recorded", 
                Arrays.<TypeReference<?>>asList(),
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}, new TypeReference<Utf8String>() {}, new TypeReference<Uint256>() {}));
        List<EventValues> valueList = extractEventParameters(event, transactionReceipt);
        ArrayList<RecordedEventResponse> responses = new ArrayList<RecordedEventResponse>(valueList.size());
        for (EventValues eventValues : valueList) {
            RecordedEventResponse typedResponse = new RecordedEventResponse();
            typedResponse._sender = (String) eventValues.getNonIndexedValues().get(0).getValue();
            typedResponse._text = (String) eventValues.getNonIndexedValues().get(1).getValue();
            typedResponse._time = (BigInteger) eventValues.getNonIndexedValues().get(2).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<RecordedEventResponse> recordedEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        final Event event = new Event("Recorded", 
                Arrays.<TypeReference<?>>asList(),
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}, new TypeReference<Utf8String>() {}, new TypeReference<Uint256>() {}));
        AppFilter filter = new AppFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(event));
        return citaj.appLogFlowable(filter).map(new Function<Log, RecordedEventResponse>() {
            @Override
            public RecordedEventResponse apply(Log log) {
                EventValues eventValues = extractEventParameters(event, log);
                RecordedEventResponse typedResponse = new RecordedEventResponse();
                typedResponse._sender = (String) eventValues.getNonIndexedValues().get(0).getValue();
                typedResponse._text = (String) eventValues.getNonIndexedValues().get(1).getValue();
                typedResponse._time = (BigInteger) eventValues.getNonIndexedValues().get(2).getValue();
                return typedResponse;
            }
        });
    }

    public RemoteCall<TransactionReceipt> add(String text, BigInteger time, Long quota, String nonce, Long validUntilBlock, Integer version, BigInteger chainId, String value) {
        com.citahub.cita.abi.datatypes.Function function = new com.citahub.cita.abi.datatypes.Function(
                "add", 
                Arrays.<Type>asList(new com.citahub.cita.abi.datatypes.Utf8String(text), 
                new com.citahub.cita.abi.datatypes.generated.Uint256(time)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function, quota, nonce, validUntilBlock, version, chainId, value);
    }

    public RemoteCall<List> getList() {
        com.citahub.cita.abi.datatypes.Function function = new com.citahub.cita.abi.datatypes.Function("getList", 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<DynamicArray<Uint256>>() {}));
        return executeRemoteCallSingleValueReturn(function, List.class);
    }

    public RemoteCall<String> get(BigInteger time) {
        com.citahub.cita.abi.datatypes.Function function = new com.citahub.cita.abi.datatypes.Function("get", 
                Arrays.<Type>asList(new com.citahub.cita.abi.datatypes.generated.Uint256(time)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public static RemoteCall<Abi> deploy(CITAj citaj, TransactionManager transactionManager, Long quota, String nonce, Long validUntilBlock, Integer version, String value, BigInteger chainId) {
        return deployRemoteCall(Abi.class, citaj, transactionManager, quota, nonce, validUntilBlock, version, chainId, value, BINARY, "");
    }

    public static Abi load(String contractAddress, CITAj citaj, TransactionManager transactionManager) {
        return new Abi(contractAddress, citaj, transactionManager);
    }

    public static class RecordedEventResponse {
        public String _sender;

        public String _text;

        public BigInteger _time;
    }
}
