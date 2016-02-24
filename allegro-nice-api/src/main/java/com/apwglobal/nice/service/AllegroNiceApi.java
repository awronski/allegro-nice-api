package com.apwglobal.nice.service;

import com.apwglobal.nice.auction.AuctionService;
import com.apwglobal.nice.client.ClientService;
import com.apwglobal.nice.country.InfoService;
import com.apwglobal.nice.deal.DealService;
import com.apwglobal.nice.domain.*;
import com.apwglobal.nice.feedback.FeedbackService;
import com.apwglobal.nice.journal.JournalService;
import com.apwglobal.nice.login.Credentials;
import com.apwglobal.nice.login.LoginService;
import com.apwglobal.nice.payment.IncomingPaymentService;
import com.apwglobal.nice.sell.SellService;
import com.apwglobal.nice.system.SystemService;
import pl.allegro.webapi.ItemPostBuyDataStruct;
import pl.allegro.webapi.ServicePort;
import pl.allegro.webapi.ServiceService;
import pl.allegro.webapi.SysStatusType;
import rx.Observable;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import javax.xml.namespace.QName;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class AllegroNiceApi extends AbstractService implements IAllegroNiceApi {

    private static final String URN_SANDBOX_WEB_API = "urn:SandboxWebApi";
    private static final String ALLEGRO_WEBAPISANDBOX_WSDL =
            "https://webapi.allegro.pl.webapisandbox.pl/service.php?wsdl";

    private AllegroSession session;
    private LoginService loginService;
    private InfoService infoService;
    private SystemService systemService;
    private JournalService journalService;
    private ClientService clientService;
    private DealService dealService;
    private AuctionService auctionService;
    private SellService sellService;
    private FeedbackService feedbackService;
    private IncomingPaymentService incomingPaymentService;

    private AllegroNiceApi(Builder builder) {
        allegro = createAllegro(builder);
        cred = builder.cred;
        conf = builder.conf;

        loginService = new LoginService(allegro, cred, conf);
        infoService = new InfoService(allegro, cred, conf);
        systemService = new SystemService(allegro, cred, conf);
        journalService = new JournalService(allegro, cred, conf);
        clientService = new ClientService(allegro, cred, conf);
        dealService = new DealService(allegro, cred, conf, infoService);
        auctionService = new AuctionService(allegro, cred, conf);
        sellService = new SellService(allegro, cred, conf);
        feedbackService = new FeedbackService(allegro, cred, conf);
        incomingPaymentService = new IncomingPaymentService(allegro, cred, conf);
    }

    private ServicePort createAllegro(Builder builder) {
        ServicePort allegro;

        if (builder.test) {
            QName SERVICE_PORT = new QName(URN_SANDBOX_WEB_API, "servicePort");
            QName SERVICE_NAME = new QName(URN_SANDBOX_WEB_API, "serviceService");
            allegro = new ServiceService(getWsdlLocation(), SERVICE_NAME).getPort(SERVICE_PORT, ServicePort.class);
        } else {
            allegro = new ServiceService().getServicePort();
        }

        return allegro;
    }

    private URL getWsdlLocation() {
        try {
            return new URL(ALLEGRO_WEBAPISANDBOX_WSDL);
        } catch (MalformedURLException e) {
            throw new IllegalStateException(e.getMessage(), e);
        }
    }

    @Override
    public IAllegroNiceApi login() {
        this.session = loginService.login();
        return this;
    }

    @Override
    public long getClientId() {
        return cred.getClientId();
    }

    @Override
    public AllegroSession getSession() {
        return session;
    }

    @Override
    public Map<Integer, String> getCountries() {
        return infoService.getCountries();
    }

    @Override
    public Map<Integer, String> getShippment() {
        return infoService.getShipping();
    }

    @Override
    public SysStatusType getStatus() {
        return systemService.getStatus();
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
    public Observable<Payment> getPayments(Observable<Deal> deals) {
        return dealService.getPayments(session.getSessionId(), cred.getClientId(), deals);
    }

    @Override
    public Observable<Auction> getAuctions() {
        return auctionService.getAuctions(session.getSessionId());
    }

    @Override
    public Optional<Auction> getAuctionById(long itemId) {
        return auctionService.getAuctionById(session.getSessionId(), itemId);
    }

    public List<AuctionField> getAuctionFields(long itemId) {
        return auctionService.getAuctionFields(session.getSessionId(), itemId);
    }

    @Override
    public ChangedAuctionInfo changeAuctions(long itemId, List<AuctionField> fieldsToModify) {
        return auctionService.changeAuctions(itemId, fieldsToModify, session.getSessionId());
    }

    @Override
    public List<Category> getCategories() {
        return sellService.getCategories();
    }

    @Override
    public List<FormField> getSellFormFields(int categoryId) {
        return sellService.getSellFormFields(categoryId);
    }

    @Override
    public NewAuctionPrice checkNewAuction(List<AuctionField> fields) {
        return sellService.checkNewAuction(fields, session.getSessionId());
    }

    @Override
    public CreatedAuction createNewAuction(List<AuctionField> fields) {
        return sellService.createNewAuction(fields, session.getSessionId());
    }

    @Override
    public ChangedQty changeQty(long itemId, int newQty) {
        return sellService.changeAuctionQty(session.getSessionId(), itemId, newQty);
    }

    @Override
    public String changePrice(long itemId, double newPrice) {
        return sellService.changeAuctionPrice(session.getSessionId(), itemId, newPrice);
    }

    @Override
    public List<FinishAuctionFailure> finishAuctions(List<Long> itemsIds) {
        return sellService.finishAuctions(session.getSessionId(), itemsIds);
    }

    @Override
    public int getWaintingFeedbackCounter() {
        return feedbackService.getWaitingFeedbackCounter(session.getSessionId());
    }

    @Override
    public Observable<WaitingFeedback> getWaitingFeedbacks() {
        return feedbackService.getWaitingFeedbacks(session.getSessionId());
    }

    @Override
    public List<CreatedFeedback> createFeedbacks(List<CreateFeedback> feedbacks) {
        return feedbackService.createFeedbacks(session.getSessionId(), feedbacks);
    }

    @Override
    public Observable<IncomingPayment> getIncomingPayments() {
        return incomingPaymentService.getIncomingPayments(session.getSessionId());
    }

    public static final class Builder {
        private Credentials cred;
        private Configuration conf;
        private boolean test;

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

        public Builder test(boolean test) {
            if (test) {
                this.test = true;
                allegroIncorrectCertificateWorkaround();
            }
            return this;
        }

        public AllegroNiceApi build() {
            return new AllegroNiceApi(this);
        }
    }

    /*
    * Workaround for allegro ssl cert bug
     */
    private static void allegroIncorrectCertificateWorkaround() {
//        Properties systemProperties = System.getProperties();
//        systemProperties.setProperty( "http.proxyHost", "127.0.0.1" );
//        systemProperties.setProperty( "http.proxyPort", "8888" );
//        systemProperties.setProperty( "https.proxyHost", "127.0.0.1" );
//        systemProperties.setProperty( "https.proxyPort", "8888" );
        TrustManager[] trustAllCerts = new TrustManager[]{new X509TrustManager() {
            public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                return null;
            }

            public void checkClientTrusted(java.security.cert.X509Certificate[] certs, String authType) {
            }

            public void checkServerTrusted(java.security.cert.X509Certificate[] certs, String authType) {
            }
        }};

        try {
            SSLContext sc = SSLContext.getInstance("SSL");
            sc.init(null, trustAllCerts, new java.security.SecureRandom());
            HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

}
