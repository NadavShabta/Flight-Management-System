import java.time.ZoneId;
import java.util.Map;
import java.util.Set;

public class TimezoneMapper {
    private static final Map<String, String> airportTimeZones = Map.ofEntries(
            Map.entry("JFK", "America/New_York"), // New York
            Map.entry("LAX", "America/Los_Angeles"), // Los Angeles
            Map.entry("ORD", "America/Chicago"), // Chicago O'Hare
            Map.entry("DFW", "America/Chicago"), // Dallas/Fort Worth
            Map.entry("DEN", "America/Denver"), // Denver
            Map.entry("ATL", "America/New_York"), // Atlanta
            Map.entry("CDG", "Europe/Paris"), // Paris Charles de Gaulle
            Map.entry("LHR", "Europe/London"), // London Heathrow
            Map.entry("FRA", "Europe/Berlin"), // Frankfurt
            Map.entry("AMS", "Europe/Amsterdam"), // Amsterdam
            Map.entry("MAD", "Europe/Madrid"), // Madrid
            Map.entry("BCN", "Europe/Madrid"), // Barcelona
            Map.entry("DXB", "Asia/Dubai"), // Dubai
            Map.entry("HND", "Asia/Tokyo"), // Tokyo Haneda
            Map.entry("NRT", "Asia/Tokyo"), // Tokyo Narita
            Map.entry("SIN", "Asia/Singapore"), // Singapore Changi
            Map.entry("HKG", "Asia/Hong_Kong"), // Hong Kong
            Map.entry("BKK", "Asia/Bangkok"), // Bangkok
            Map.entry("PEK", "Asia/Shanghai"), // Beijing Capital
            Map.entry("PVG", "Asia/Shanghai"), // Shanghai Pudong
            Map.entry("SYD", "Australia/Sydney"), // Sydney
            Map.entry("MEL", "Australia/Melbourne"), // Melbourne
            Map.entry("GRU", "America/Sao_Paulo"), // São Paulo–Guarulhos
            Map.entry("GIG", "America/Sao_Paulo"), // Rio de Janeiro–Galeão
            Map.entry("MEX", "America/Mexico_City"), // Mexico City
            Map.entry("CUN", "America/Cancun"), // Cancun
            Map.entry("YYZ", "America/Toronto"), // Toronto Pearson
            Map.entry("YVR", "America/Vancouver"), // Vancouver
            Map.entry("SVO", "Europe/Moscow"), // Moscow Sheremetyevo
            Map.entry("DME", "Europe/Moscow"), // Moscow Domodedovo
            Map.entry("IST", "Europe/Istanbul"), // Istanbul
            Map.entry("FCO", "Europe/Rome"), // Rome Fiumicino
            Map.entry("MUC", "Europe/Berlin"), // Munich
            Map.entry("ZRH", "Europe/Zurich"), // Zurich
            Map.entry("LGW", "Europe/London"), // London Gatwick
            Map.entry("CPT", "Africa/Johannesburg"), // Cape Town
            Map.entry("JNB", "Africa/Johannesburg"), // Johannesburg
            Map.entry("RUH", "Asia/Riyadh"), // Riyadh
            Map.entry("DOH", "Asia/Qatar"), // Doha
            Map.entry("DEL", "Asia/Kolkata"), // Delhi
            Map.entry("BOM", "Asia/Kolkata"), // Mumbai
            Map.entry("SEA", "America/Los_Angeles"), // Seattle
            Map.entry("SFO", "America/Los_Angeles"), // San Francisco
            Map.entry("IAH", "America/Chicago"), // Houston
            Map.entry("LAS", "America/Los_Angeles"), // Las Vegas
            Map.entry("MCO", "America/New_York"), // Orlando
            Map.entry("MIA", "America/New_York"), // Miami
            Map.entry("PHX", "America/Phoenix"), // Phoenix
            Map.entry("CLT", "America/New_York"), // Charlotte
            Map.entry("EWR", "America/New_York"), // Newark
            Map.entry("MSP", "America/Chicago"), // Minneapolis
            Map.entry("DTW", "America/Detroit") ,// Detroit
            Map.entry("TLV", "Asia/Jerusalem"), // Tel Aviv, Israel
            Map.entry("BOS", "America/New_York"), // Boston, USA
            Map.entry("IAD", "America/New_York"), // Washington Dulles, USA
            Map.entry("FLL", "America/New_York"), // Fort Lauderdale, USA
            Map.entry("SAN", "America/Los_Angeles"), // San Diego, USA
            Map.entry("MDW", "America/Chicago"), // Chicago Midway, USA
            Map.entry("DCA", "America/New_York"), // Ronald Reagan Washington, USA
            Map.entry("PHL", "America/New_York"), // Philadelphia, USA
            Map.entry("BWI", "America/New_York"), // Baltimore-Washington, USA
            Map.entry("CJU", "Asia/Seoul"), // Jeju, South Korea
            Map.entry("ICN", "Asia/Seoul"), // Incheon, South Korea
            Map.entry("KIX", "Asia/Tokyo"), // Kansai, Japan
            Map.entry("ITM", "Asia/Tokyo"), // Osaka Itami, Japan
            Map.entry("CTS", "Asia/Tokyo"), // New Chitose, Japan
            Map.entry("NGO", "Asia/Tokyo"), // Chubu Centrair, Japan
            Map.entry("CAN", "Asia/Shanghai"), // Guangzhou, China
            Map.entry("SHA", "Asia/Shanghai"), // Shanghai Hongqiao, China
            Map.entry("CTU", "Asia/Shanghai"), // Chengdu, China
            Map.entry("SZX", "Asia/Shanghai"), // Shenzhen, China
            Map.entry("HAN", "Asia/Ho_Chi_Minh"), // Hanoi, Vietnam
            Map.entry("SGN", "Asia/Ho_Chi_Minh"), // Ho Chi Minh City, Vietnam
            Map.entry("BNE", "Australia/Brisbane"), // Brisbane, Australia
            Map.entry("PER", "Australia/Perth"), // Perth, Australia
            Map.entry("ADL", "Australia/Adelaide"), // Adelaide, Australia
            Map.entry("CNS", "Australia/Brisbane"), // Cairns, Australia
            Map.entry("AKL", "Pacific/Auckland"), // Auckland, New Zealand
            Map.entry("WLG", "Pacific/Auckland"), // Wellington, New Zealand
            Map.entry("CHC", "Pacific/Auckland"), // Christchurch, New Zealand
            Map.entry("AUH", "Asia/Dubai"), // Abu Dhabi, UAE
            Map.entry("JED", "Asia/Riyadh"), // Jeddah, Saudi Arabia
            Map.entry("DMM", "Asia/Riyadh"), // Dammam, Saudi Arabia
            Map.entry("BAH", "Asia/Bahrain"), // Bahrain
            Map.entry("MCT", "Asia/Muscat"), // Muscat, Oman
            Map.entry("KWI", "Asia/Kuwait"), // Kuwait City, Kuwait
            Map.entry("BEY", "Asia/Beirut"), // Beirut, Lebanon
            Map.entry("AMM", "Asia/Amman"), // Amman, Jordan
            Map.entry("CAI", "Africa/Cairo"), // Cairo, Egypt
            Map.entry("ALG", "Africa/Algiers"), // Algiers, Algeria
            Map.entry("CMN", "Africa/Casablanca"), // Casablanca, Morocco
            Map.entry("TUN", "Africa/Tunis")// Tunis, Tunisia
    );

    public static ZoneId getZoneIdForAirport(String airportCode) {
        String timeZone = airportTimeZones.getOrDefault(airportCode.toUpperCase(), "UTC"); // Defaulting to UTC
        return ZoneId.of(timeZone);
    }
    public static Set<String> getAvailableAirportCodes() {
        return airportTimeZones.keySet(); // Returns a Set of all keys (airport codes) in the map
    }
    public static void addAirportCode(String code, String timeZone) {
        airportTimeZones.put(code.toUpperCase(), timeZone);
    }
    public static boolean doesAirportCodeExist(String code) {
        return airportTimeZones.containsKey(code);
    }
}
