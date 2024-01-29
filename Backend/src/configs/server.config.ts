export default {
    port: Number(process.env.PORT || 3000),
    host: process.env.HOST || "localhost",
    signatureKey: process.env.SIGNATURE_KEY,
    clientKey: process.env.CLIENT_KEY,
    appKey: process.env.APP_KEY,
}