package com.cms.payment;

import com.paypal.api.payments.*;
import com.paypal.base.rest.APIContext;
import com.paypal.base.rest.PayPalRESTException;
import org.springframework.stereotype.Service;

public class PaypalService {

	private APIContext apiContext;

	public PaypalService(APIContext apiContext) {
		this.apiContext = apiContext;
	}

	public Payment createPayment(
			Double total,
			String currency,
			String method,
			String intent,
			String description,
			String cancelUrl,
			String successUrl) throws PayPalRESTException {

		// Create details for the transaction
		Details details = new Details();
		details.setSubtotal(String.format("%.2f", total));

		// Create amount with details
		Amount amount = new Amount();
		amount.setCurrency(currency);
		amount.setTotal(String.format("%.2f", total));
		amount.setDetails(details);

		// Create transaction
		Transaction transaction = new Transaction();
		transaction.setAmount(amount);
		transaction.setDescription(description);

		// Create redirect URLs
		RedirectUrls redirectUrls = new RedirectUrls();
		redirectUrls.setCancelUrl(cancelUrl);
		redirectUrls.setReturnUrl(successUrl);

		// Create payment object
		Payment payment = new Payment();
		payment.setIntent(intent);
		payment.setPayer(new Payer().setPaymentMethod(method));
		payment.setTransactions(java.util.Arrays.asList(transaction));
		payment.setRedirectUrls(redirectUrls);

		// Create payment using API context
		return payment.create(apiContext);
	}

	public Payment executePayment(String paymentId, String payerId) throws PayPalRESTException {
		PaymentExecution paymentExecution = new PaymentExecution();
		paymentExecution.setPayerId(payerId);

		Payment payment = new Payment();
		payment.setId(paymentId);

		// Execute payment using API context
		return payment.execute(apiContext, paymentExecution);
	}
}

