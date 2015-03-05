package com.apwglobal.nice.service;

import com.apwglobal.nice.auction.AuctionService;
import com.apwglobal.nice.client.ClientService;
import com.apwglobal.nice.deal.DealService;
import com.apwglobal.nice.domain.Auction;
import com.apwglobal.nice.domain.Deal;
import com.apwglobal.nice.domain.Journal;
import com.apwglobal.nice.journal.JournalService;
import com.apwglobal.nice.login.Credentials;
import com.apwglobal.nice.login.LoginService;
import com.apwglobal.nice.message.AllegroMessage;
import com.apwglobal.nice.message.MessageService;
import com.apwglobal.nice.system.SystemService;
import pl.allegro.webapi.ItemPostBuyDataStruct;
import pl.allegro.webapi.ServiceService;
import pl.allegro.webapi.SysStatusType;
import rx.Observable;

import java.time.LocalDateTime;
import java.util.List;

public class AllegroNiceApi extends AbstractService implements IAllegroNiceApi {

    private AllegroSession session;
    private LoginService loginService;
    private SystemService systemService;
    private MessageService messageService;
    private JournalService journalService;
    private ClientService clientService;
    private DealService dealService;
    private AuctionService auctionService;

    private AllegroNiceApi(Builder builder) {
        allegro = new ServiceService().getServicePort();
        cred = builder.cred;
        conf = builder.conf;

        loginService = new LoginService(allegro, cred, conf);
        systemService = new SystemService(allegro, cred, conf);
        messageService = new MessageService(allegro, cred, conf);
        journalService = new JournalService(allegro, cred, conf);
        clientService = new ClientService(allegro, cred, conf);
        dealService = new DealService(allegro, cred, conf);
        auctionService = new AuctionService(allegro, cred, conf);
    }

    @Override
    public IAllegroNiceApi login() {
        this.session = loginService.login();
        return this;
    }

    @Override
    public AllegroSession getSession() {
        return session;
    }

    @Override
    public SysStatusType getStatus() {
        return systemService.getStatus();
    }

    @Override
    public List<AllegroMessage> getAllMessages(LocalDateTime from) {
        return messageService.getAllMessages(from);
    }

    @Override
    public Observable<Journal> getJournal(long startingPoint) {
        return journalService.getJournal(session.getSessionId(), startingPoint);
    }

    @Override
    public List<ItemPostBuyDataStruct> getClientsDate(long itemId) {
        return clientService.getClientsDate(session.getSessionId(), itemId);
    }

    @Override
    public Observable<Deal> getDeals(long startingPoint) {
        return dealService.getDeals(session.getSessionId(), startingPoint);
    }

    @Override
    public void fillBuyersForms(List<Deal> deals) {
        dealService.fillBuyersForms(session.getSessionId(), deals);
    }

    @Override
    public Observable<Auction> getAuctions() {
        return auctionService.getAuctions(session.getSessionId());
    }

    public static final class Builder {
        private Credentials cred;
        private Configuration conf;

        public Builder() {
        }

        public Builder cred(Credentials cred) {
            this.cred = cred;
            return this;
        }

        public Builder conf(Configuration conf) {
            this.conf = conf;
            return this;
        }

        public AllegroNiceApi build() {
            return new AllegroNiceApi(this);
        }
    }
}
