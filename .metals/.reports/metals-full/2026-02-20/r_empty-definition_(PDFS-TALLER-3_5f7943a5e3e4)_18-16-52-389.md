error id: file:///C:/Users/aleja/OneDrive/Desktop/7%20SEMESTRE/PFSD/PrimerTercio/PDFS-TALLER-3/Main.scala:_empty_/Main$package.validarInversion().
file:///C:/Users/aleja/OneDrive/Desktop/7%20SEMESTRE/PFSD/PrimerTercio/PDFS-TALLER-3/Main.scala
empty definition using pc, found symbol in pc: 
found definition using semanticdb; symbol _empty_/Main$package.validarInversion().
empty definition using fallback
non-local guesses:

offset: 5602
uri: file:///C:/Users/aleja/OneDrive/Desktop/7%20SEMESTRE/PFSD/PrimerTercio/PDFS-TALLER-3/Main.scala
text:
```scala
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

// Ejercicio 2 – Validacion de Inversiones antes del Analisis
// Contexto:
// Antes de incluir una inversión en los reportes oficiales, el banco debe verificar que:
// El monto invertido sea valido
// El rendimiento este dentro de rangos aceptables
// El nivel de riesgo sea reconocido por la entidad
// Objetivo:
// Diseñar un proceso que determine si una inversión puede ser analizada o si debe ser rechazada, indicando la razon.
// Resultado Esperado:
// Una inversion aprobada para analisis
// O una explicación clara del motivo de rechazo

//Paso 1: Definir el tipo de resultado
type ValidationResult[A] = Either[String, InvestmentRecord] //String para mensaje de error, InvestmentRecord inversion validada

//Paso 2: Validar el monto invertido
def validarMonto(record: InvestmentRecord) : ValidationResult[InvestmentRecord] = {
   if (record.investedAmount > 0) {
    Right(record)
   } else {
     Left(s"Monto inválido para ${record.id}: debe ser mayor a 0") 
   } 
}

//Paso 3: Validar redimiento
def validarRendimiento(record: InvestmentRecord): ValidationResult[InvestmentRecord] = {
    if((-10 <= record.monthlyReturn) && (record.monthlyReturn <= 10)) {
        Right(record)
    } else {
        Left(s"Rendimiento fuera de rango para ${record.id}:: debe estar entre -10% y 10%")
    }
}

//Paso 3: Validar nivel de riego
def validarNivelRiesgo(record: InvestmentRecord): ValidationResult[InvestmentRecord] = {

    val riesgosValidos: Set[String] = Set("LOW", "MEDIUM", "HIGH")

    if(riesgosValidos.contains(record.riskLevel)) {
        Right(record)
    } else {
        Left(s"Nivel de riesgo no reconocido: ${record.riskLevel}")
    }
}

//Paso 4: Encadenar las validaciones con for-comprehension
def validarInversion(record: InvestmentRecord): ValidationResult[InvestmentRecord] = {
  for {
    r1 <- validarMonto(record)           // Si falla, se detiene aqui y retorna Left
    r2 <- validarRendimiento(r1)         // Solo se ejecuta si r1 fue Right
    r3 <- validarNivelRiesgo(r2)         // Solo se ejecuta si r2 fue Right
  } yield r3  // Si todas pasaron, retorna Right(record)
}

//Paso 5: Mostrar resutado de analisis con pattern matching
def (record: InvestmentRecord): Unit = {
    validarInver@@sion(record) match {
        case Right(inv) => println(s"${inv.id} aprobada para análisis")
        case Left(error) => println(s"Rechazada: $error")
    } 
}

//Paso 6: Validar todo el dataset
def validarPortafolio(inversiones: List[InvestmentRecord]): Unit = {
  inversiones.map(inv => )
}


@main def ejecutar(): Unit = {
  println("Taller 3 - Programación Funcional\n")

  println("Ejercicio 1 - Estructura y recursion:")
  val gananciaTotalPortafolio = calcularGananciaTotal(dataset)
  println(s"Ganancia total del portafolio: $$${gananciaTotalPortafolio}\n")
}
```


#### Short summary: 

empty definition using pc, found symbol in pc: 