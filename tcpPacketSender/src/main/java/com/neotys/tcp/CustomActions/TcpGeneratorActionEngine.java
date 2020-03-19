package com.neotys.tcp.CustomActions;

import com.google.common.base.Optional;
import com.neotys.action.result.ResultFactory;
import com.neotys.extensions.action.ActionParameter;
import com.neotys.extensions.action.engine.ActionEngine;
import com.neotys.extensions.action.engine.Context;
import com.neotys.extensions.action.engine.Logger;
import com.neotys.extensions.action.engine.SampleResult;
import com.neotys.tcp.ClientGenerator.NeoLoadTCPClient;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.neotys.action.argument.Arguments.getArgumentLogString;
import static com.neotys.action.argument.Arguments.parseArguments;

public class TcpGeneratorActionEngine implements ActionEngine {


    private static final String STATUS_CODE_INVALID_PARAMETER = "NL-TCP-GENERATOR_ACTION-01";
    private static final String STATUS_CODE_TECHNICAL_ERROR = "NL-TCP-GENERATOR_ACTION-02";
    private static final String STATUS_CODE_BAD_CONTEXT = "NL-TCP-GENERATOR_ACTION-03";
    private static final String NLWEB_VERSION ="v1" ;

    public SampleResult execute(Context context, List<ActionParameter> parameters) {
        final SampleResult sampleResult = new SampleResult();
        final StringBuilder requestBuilder = new StringBuilder();
        final StringBuilder responseBuilder = new StringBuilder();

        final Map<String, Optional<String>> parsedArgs;
        try {
            parsedArgs = parseArguments(parameters, TcpGeneratorOption.values());
        } catch (final IllegalArgumentException iae) {
            return ResultFactory.newErrorResult(context, STATUS_CODE_INVALID_PARAMETER, "Could not parse arguments: ", iae);
        }


        final String host = parsedArgs.get(TcpGeneratorOption.Host.getName()).get();
        final String port  = parsedArgs.get(TcpGeneratorOption.Port.getName()).get();
        final String packageSize = parsedArgs.get(TcpGeneratorOption.Size.getName()).get();


        final Logger logger = context.getLogger();
        if (logger.isDebugEnabled()) {
            logger.debug("Executing " + this.getClass().getName() + " with parameters: "
                    + getArgumentLogString(parsedArgs, TcpGeneratorOption.values()));
        }


        try {
            NeoLoadTCPClient neoLoadTCPClient=new NeoLoadTCPClient(host,port,packageSize);
            neoLoadTCPClient.senData(context,sampleResult);

        }catch (Exception e) {
            return ResultFactory.newErrorResult(context, STATUS_CODE_TECHNICAL_ERROR, "HTtml Unit technical Error ", e);
        }




        return sampleResult;
    }



    private void appendLineToStringBuilder(final StringBuilder sb, final String line) {
        sb.append(line).append("\n");
    }

    /**
     * This method allows to easily create an error result and log exception.
     */
    private static SampleResult getErrorResult(final Context context, final SampleResult result, final String errorMessage, final Exception exception) {
        result.setError(true);
        result.setStatusCode("NL-TCP-GENERATOR_ERROR");
        result.setResponseContent(errorMessage);
        if (exception != null) {
            context.getLogger().error(errorMessage, exception);
        } else {
            context.getLogger().error(errorMessage);
        }
        return result;
    }

    @Override
    public void stopExecute() {
        // TODO add code executed when the test have to stop.

    }
}