package ma.enset.bankaccountservice.exceptions;

import graphql.GraphQLError;
import graphql.GraphqlErrorBuilder;
import graphql.schema.DataFetchingEnvironment;
import org.springframework.graphql.execution.DataFetcherExceptionResolverAdapter;
import org.springframework.graphql.execution.ErrorType;
import org.springframework.stereotype.Component;

/** Renvoie le message de l'exception dans la réponse GraphQL au lieu de "INTERNAL_ERROR". */
@Component
public class GraphQLExceptionHandler extends DataFetcherExceptionResolverAdapter {
    @Override
    protected GraphQLError resolveToSingleError(Throwable ex, DataFetchingEnvironment env) {
        ErrorType type = (ex instanceof CompteNotFoundException || ex instanceof ClientNotFoundException)
                ? ErrorType.NOT_FOUND : ErrorType.INTERNAL_ERROR;
        return GraphqlErrorBuilder.newError(env)
                .errorType(type)
                .message(ex.getMessage())
                .build();
    }
}
