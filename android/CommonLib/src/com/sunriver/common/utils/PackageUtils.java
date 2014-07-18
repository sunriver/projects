package com.sunriver.common.utils;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;


import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.pm.Signature;
import android.util.Log;

public class PackageUtils {
	private final static String LOG_TAG = PackageUtils.class.getSimpleName();
	
	public static String getCertificate(Context context) {
		PackageManager pm = context.getPackageManager();
		PackageInfo pi = null;
		try {
			pi = pm.getPackageInfo(context.getPackageName(), PackageManager.GET_SIGNATURES);
			Signature[] arrSignatures = pi.signatures;
			StringBuffer sb = new StringBuffer();
			for (Signature sig : arrSignatures) {
				/*
				 * Get the X.509 certificate.
				 */
				byte[] rawCert = sig.toByteArray();
				InputStream certStream = new ByteArrayInputStream(rawCert);

				CertificateFactory certFactory = null;
				try {
					certFactory = CertificateFactory.getInstance("X509");
				} catch (CertificateException e) {
					Log.e(LOG_TAG, "Fail to get CertificateFactory");
				}

				X509Certificate x509Cert = null;
				try {
					x509Cert = (X509Certificate) certFactory
							.generateCertificate(certStream);
				} catch (CertificateException e) {
					Log.e(LOG_TAG, "Fail to generate Certificate", e);
				}
				sb.append("Certificate subject: " + x509Cert.getSubjectDN()
						+ "<br>");
				sb.append("Certificate issuer: " + x509Cert.getIssuerDN()
						+ "<br>");
				sb.append("Certificate serial number: "
						+ x509Cert.getSerialNumber() + "<br>");
			}
			Log.d(LOG_TAG, "sb=" + sb.toString());
			return sb.toString();
		} catch (NameNotFoundException e) {
			Log.e(LOG_TAG, "Fail to get certificate", e);
		}
		return null;
	}
	
	public static PackageInfo getPackageInfo(final Context ctx) throws NameNotFoundException  {
		String packageName = ctx.getPackageName();
		PackageInfo pinfo = ctx.getPackageManager().getPackageInfo(packageName, 0);
		return pinfo;
	}
}
