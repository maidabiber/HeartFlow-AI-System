
# HeartFlow AI – Cardiovascular Health Platform

 Dokumentacija za ovaj projekat izrađena je timski u okviru predmeta **Osnove informacionih sistema** na Elektrotehničkom fakultetu u Sarajevu. Implementaciju sistema odlučila sam realizovati samostalno, kao proširenje projektnog zadatka, s ciljem učenja novih tehnologija i izgradnje portfolio projekta.

## O projektu

HeartFlow je AI-bazirana platforma za podršku u prevenciji i liječenju kardiovaskularnih bolesti. Sistem omogućava kardiolozima i medicinskom osoblju praćenje pacijenata, analizu vitalnih parametara i procjenu rizika od kardiovaskularnih događaja uz pomoć vještačke inteligencije.

Originalna ideja i dokumentacija (zahtjevi, use case dijagrami, ER dijagram, UAT testovi) izrađeni su u timu od 10 studenata. Tehnička implementacija je samostalni rad.

## Tech Stack

| Sloj | Tehnologija |
|------|------------|
| Frontend | Angular 17+ (Standalone Components) |
| Backend | Spring Boot 3.5, Java 21 |
| Baza podataka | PostgreSQL 16 (Docker) |
| AI asistent | Ollama + Llama3.2:1b (lokalno) |
| Sigurnost | JWT + Spring Security + BCrypt |
| Infrastruktura | Docker, Docker Compose |

## Funkcionalnosti

- **Autentifikacija** — JWT login sa ulogama (DOCTOR, NURSE, ADMIN)
- **Upravljanje pacijentima** — dodavanje, pregled, pretraga pacijenata
- **Vitalni parametri** — unos i praćenje srčane frekvencije, krvnog pritiska, HRV, saturacije
- **AI procjena rizika** — automatski kalkulator kardiovaskularnog rizika baziran na vitalnim parametrima
- **AI chat asistent** — konverzacijski asistent pokrenut lokalno putem Ollama (Llama3.2)

## Pokretanje projekta

### Preduslovi
- Java 21
- Node.js 18+
- Docker Desktop
- Ollama

### Backend
```bash
cd heartflow-api
docker compose up -d
./mvnw spring-boot:run
```

### Frontend
```bash
cd heartflow-ui
npm install
npx ng serve
```

### AI asistent
```bash
ollama pull llama3.2:1b
ollama serve
```

Aplikacija dostupna na: `http://localhost:4200`