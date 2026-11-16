body {
    font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
    background: linear-gradient(180deg, #f4f8ff 0%, #eef4ff 100%);
    color: #1d2433;
}

.hero-section {
    background: linear-gradient(135deg, #0f172a 0%, #1d4ed8 100%);
    color: white;
    min-height: 420px;
}

.hero-card, .feature-card, .dashboard-card, .info-panel {
    background: rgba(255, 255, 255, 0.12);
    border: 1px solid rgba(255, 255, 255, 0.15);
    border-radius: 20px;
    box-shadow: 0 15px 35px rgba(15, 23, 42, 0.12);
}

.feature-card {
    background: white;
    padding: 2rem 1.5rem;
    text-align: center;
    border: 1px solid #e6eefb;
    transition: 0.3s ease;
}

.feature-card:hover {
    transform: translateY(-6px);
    box-shadow: 0 18px 38px rgba(29, 78, 216, 0.12);
}

.icon-box {
    width: 70px;
    height: 70px;
    border-radius: 18px;
    margin: 0 auto 1rem;
    display: flex;
    align-items: center;
    justify-content: center;
    background: linear-gradient(135deg, #dbeafe 0%, #bfdbfe 100%);
    font-size: 2rem;
}

.section-title {
    font-weight: 700;
    color: #0f172a;
}

.about-section {
    background: #f8fbff;
}

.info-panel {
    background: linear-gradient(135deg, #eff6ff 0%, #dbeafe 100%);
    border: 1px solid #dbeafe;
    padding: 2rem;
    color: #111827;
}

.info-panel ul {
    margin: 0;
    padding-left: 1.1rem;
    line-height: 2;
}

.stat-box {
    padding: 1rem 1.2rem;
    border-radius: 16px;
    background: rgba(255, 255, 255, 0.12);
    border: 1px solid rgba(255, 255, 255, 0.18);
}

.stat-box .label {
    display: block;
    font-size: 0.84rem;
    opacity: 0.8;
}

.stat-box strong {
    display: block;
    font-size: 2rem;
    margin-top: 0.5rem;
}

.dashboard-card {
    padding: 1.5rem;
    min-height: 150px;
    display: flex;
    flex-direction: column;
    justify-content: center;
    color: white;
}

.dashboard-card.primary { background: linear-gradient(135deg, #2563eb 0%, #1d4ed8 100%); }
.dashboard-card.success { background: linear-gradient(135deg, #10b981 0%, #059669 100%); }
.dashboard-card.warning { background: linear-gradient(135deg, #f59e0b 0%, #d97706 100%); }
.dashboard-card.danger { background: linear-gradient(135deg, #ef4444 0%, #dc2626 100%); }

.dashboard-card .label {
    font-size: 0.85rem;
    opacity: 0.9;
    text-transform: uppercase;
    letter-spacing: 0.04em;
}

.dashboard-card .value {
    margin-top: 0.8rem;
    font-size: 2rem;
    font-weight: 700;
}

.card {
    border-radius: 20px;
}

.btn {
    border-radius: 12px;
}

.table thead th {
    background-color: #eef5ff;
}

@media (max-width: 767px) {
    .hero-section {
        min-height: 320px;
    }
    .stat-box strong {
        font-size: 1.5rem;
    }
}

@media print {
    body * {
        visibility: hidden;
    }
    .card, .card * {
        visibility: visible;
    }
    .card {
        position: absolute;
        left: 0;
        top: 0;
        width: 100%;
    }
    button, a {
        display: none !important;
    }
}
