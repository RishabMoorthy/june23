const { portalServerPort, stubServerPort } = window.__SERVER_CONFIG__ || {};
const host = window.location.hostname;
const isLocalOrDirect = host === "localhost" || /^\d+\.\d+\.\d+\.\d+$/.test(host);

const CONFIG = {
    API_BASE: isLocalOrDirect ? `http://${host}:${portalServerPort}/backend/api` : '/backend/api',
    BACKEND_API: isLocalOrDirect ? `http://${host}:${stubServerPort}/sbackend` : '/sbackend',
    StubServerIP: null
};

export default CONFIG;
