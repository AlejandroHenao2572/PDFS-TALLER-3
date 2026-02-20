import  scala.annotation.tailrec

// Caso de Uso: Módulo de Análisis de Inversiones – ECIBank
// ECIBank requiere el diseño de un módulo central para analizar el comportamiento de inversiones financieras de forma confiable, predecible y segura. 

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

// Ejercicio 1 – Ganancia Total del Portafolio
// Contexto: ECIBank necesita conocer cuánto dinero ganó o perdió un portafolio completo de inversiones durante un perido específico.
// Objetivo: Calcular la ganancia monetaria total generada por todas las inversiones del portafolio, considerando tanto el capital invertido como su rendimiento.
// Resultado: Un unico valor monetario que represente la ganancia o perdida total del portafolio.
@tailrec
def calcularGananciaTotal(inversiones: List[InvestmentRecord], acumulador: BigDecimal = BigDecimal(0)): BigDecimal = {
    inversiones match {
        case Nil => acumulador //Caso base cuando la lista esta vacia
        case x::xs => // x es el primer elemento (una inversion) y xs es el resto de la lista de inversiones
            val ganancia = x.investedAmount * BigDecimal(x.monthlyReturn / 100) //Calcular la ganancia: Ganancia = investedAmount × (monthlyReturn / 100)
            calcularGananciaTotal(xs, acumulador + ganancia) //LLamada recursiva
    }
}

@main def ejecutar(): Unit = {
  println("Taller 3 - Programación Funcional\n")

  println("Ejercicio 1 - Estructura y recursion:")
  val gananciaTotalPortafolio = calcularGananciaTotal(dataset)
  println(s"Ganancia total del portafolio: $$${gananciaTotalPortafolio}\n")
}