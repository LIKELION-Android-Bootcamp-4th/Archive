// const functions = require("firebase-functions");
const {onCall} = require("firebase-functions/v2/https");
const authV1 = require("firebase-functions/v1/auth");
// const {onAuthUserCreate} = require("firebase-functions/v2/auth");
const admin = require("firebase-admin");

admin.initializeApp();

exports.kakaoCustomAuth = onCall(require("./kakao-auth").handler);
exports.naverCustomAuth = onCall(require("./naver-auth").handler);

exports.createUserDoc = authV1.user().onCreate(async (u) => {
  const userDocRef = admin.firestore().collection("users").doc(u.uid);
  const provider = u.providerData && u.providerData.length > 0 ? u.providerData[0].providerId : "unknown";
  const userData = {
    email: u.email || "",
    name: u.displayName || "",
    photoUrl: u.photoURL || "",
    provider,
    createdAt: admin.firestore.FieldValue.serverTimestamp(),
  };

  await userDocRef.set(userData, {merge: true});
});

