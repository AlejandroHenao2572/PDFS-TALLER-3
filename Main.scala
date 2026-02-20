// Caso de Uso: Módulo de Análisis de Inversiones – ECIBank
// ECIBank requiere el diseño de un módulo central para analizar el comportamiento de inversiones financieras de forma confiable, predecible y segura. 
//El objetivo es resolver distintos problemas reales del negocio bancario, aplicando principios de programación funcional como inmutabilidad, composición y manejo explícito del contexto.

// Case Class para representar una inversion:
case class InvestmentRecord(
  id: String, // identificador de la inversión
  product: String, // nombre del producto financiero
  riskLevel: String, // nivel de riesgo (LOW, MEDIUM, HIGH)
  investedAmount: BigDecimal,  // capital invertido
  monthlyReturn: Double,       // porcentaje
  month: Int    // mes de la inversion (1-12)
)

// DataSet para pruebas:
val dataset: List[InvestmentRecord] = List(
  InvestmentRecord("INV-01", "Fondo_A", "LOW",    BigDecimal(10000), 1.2, 1),
  InvestmentRecord("INV-02", "Fondo_A", "LOW",    BigDecimal(10000), 1.5, 2),
  InvestmentRecord("INV-03", "Fondo_A", "LOW",    BigDecimal(10000), 1.3, 3),
  InvestmentRecord("INV-04", "Fondo_B", "MEDIUM", BigDecimal(8000),  2.1, 1),
  InvestmentRecord("INV-05", "Fondo_B", "MEDIUM", BigDecimal(8000),  1.9, 2),
  InvestmentRecord("INV-06", "Fondo_B", "MEDIUM", BigDecimal(8000),  2.4, 3),
  InvestmentRecord("INV-07", "Accion_NVIDA", "HIGH",  BigDecimal(5000), -1.2, 1),
  InvestmentRecord("INV-08", "Accion_APPLE", "HIGH",  BigDecimal(5000),  3.4, 2),
  InvestmentRecord("INV-09", "Accion_GOOGLE", "HIGH",  BigDecimal(5000),  2.8, 3),
  InvestmentRecord("INV-10", "Bono_C",   "LOW",   BigDecimal(15000), 0.8, 1),
  InvestmentRecord("INV-11", "Bono_C",   "LOW",   BigDecimal(15000), 0.9, 2),
  InvestmentRecord("INV-12", "Bono_C",   "LOW",   BigDecimal(15000), 1.0, 3),
  InvestmentRecord("INV-13", "ETF",    "MEDIUM",BigDecimal(12000), 1.7, 1),
  InvestmentRecord("INV-14", "ETF",    "MEDIUM",BigDecimal(12000), 1.9, 2),
  InvestmentRecord("INV-15", "ETF",    "MEDIUM",BigDecimal(12000), 2.0, 3),
  InvestmentRecord("INV-16", "BTC", "HIGH",  BigDecimal(7000),  4.1, 1),
  InvestmentRecord("INV-17", "BTC", "HIGH",  BigDecimal(7000), -0.5, 2),
  InvestmentRecord("INV-18", "BTC", "HIGH",  BigDecimal(7000),  3.2, 3),
  InvestmentRecord("INV-19", "Fondo_D",  "LOW",   BigDecimal(9000),  1.1, 1),
  InvestmentRecord("INV-20", "Fondo_D",  "LOW",   BigDecimal(9000),  1.4, 2)
)

@main def ejecutar(): Unit = {
  println("Taller 3 - Programación Funcional")
}