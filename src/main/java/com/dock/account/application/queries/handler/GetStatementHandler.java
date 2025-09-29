package com.dock.account.application.queries.handler;

import com.dock.account.application.api.response.StatementResponse;
import com.dock.account.application.queries.GetStatementQuery;
import com.dock.account.domain.models.Account;
import com.dock.account.domain.models.Transaction;
import com.dock.account.domain.vos.AccountId;
import com.dock.account.domain.repositories.AccountRepository;
import com.dock.account.domain.vos.Period;
import com.dock.common.exceptions.DomainException;
import com.dock.common.queries.QueryHandler;
import com.dock.common.utils.DateSanitizer;
import io.quarkus.arc.Unremovable;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.RequiredArgsConstructor;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;

@ApplicationScoped
@Unremovable
@RequiredArgsConstructor
public class GetStatementHandler implements QueryHandler<GetStatementQuery, StatementResponse> {

    private final AccountRepository accountRepository;

    @Override
    //TODO: melhorar paginando
    public StatementResponse handle(GetStatementQuery command) {
        final Account account = accountRepository.findById(AccountId.from(command.accountId()))
                .orElseThrow(() -> new DomainException("Conta não encontrada"));

        final ZoneId zone = ZoneId.of("America/Sao_Paulo");
        final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ISO_LOCAL_DATE;

        Instant start = LocalDate.parse(DateSanitizer.normalizeDateParam(command.start()), dateTimeFormatter)
                .atStartOfDay(zone)
                .toInstant();

        Instant end = LocalDate.parse(DateSanitizer.normalizeDateParam(command.end()), dateTimeFormatter)
                .plusDays(1)
                .atStartOfDay(zone)
                .toInstant();

        if (end.isBefore(start)) {
            throw new DomainException("Data de inicio deve ser antes da data de fim");
        }

        final var period = new Period(start, end);

        final List<Transaction> txs = account.statement(period);
        return StatementResponse.from(account, start, end, txs);
    }
}
